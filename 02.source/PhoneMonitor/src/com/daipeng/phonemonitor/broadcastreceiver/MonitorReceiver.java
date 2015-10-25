package com.daipeng.phonemonitor.broadcastreceiver;

import java.util.Date;
import java.util.Map;

import com.daipeng.phonemonitor.comon.ImmutableValues;
import com.daipeng.phonemonitor.service.MonitorService;
import com.daipeng.phonemonitor.tasks.AsyncMailSendTask;
import com.daipeng.phonemonitor.utils.ContactUtils;
import com.daipeng.phonemonitor.utils.DateUtils;
import com.daipeng.phonemonitor.utils.LogUtils;
import com.daipeng.phonemonitor.utils.MailConfig;
import com.daipeng.phonemonitor.utils.MailSenderUtils;
import com.daipeng.phonemonitor.utils.NetworkUtils;
import com.daipeng.phonemonitor.utils.PrefsUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony.Sms.Intents;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class MonitorReceiver extends BroadcastReceiver {
	//the phone status previous time
	private String preStatus = ""; 
	//configuration for mail
	private MailConfig mailConfig;
	//the time when start ring
	private Date startRingTime = null;
	
	private boolean isPhoneEnable;
	private boolean isSmsEnable;
	private boolean isBatteryEnable;
	
	public MonitorReceiver(MonitorService monitorService){
		Map<String, String> settings = PrefsUtils.read(monitorService, ImmutableValues.APP_CONF_FILE_NAME);
		mailConfig = MailConfig.loadMailConfig(settings);
		
		//load setting
		 
		isPhoneEnable = ImmutableValues.FLG_ON.equals(settings.get(ImmutableValues.APP_CONF_PHONEFLG_KEY))?true:false;
		isSmsEnable = ImmutableValues.FLG_ON.equals(settings.get(ImmutableValues.APP_CONF_SMSFLG_KEY))?true:false;
		isBatteryEnable = ImmutableValues.FLG_ON.equals(settings.get(ImmutableValues.APP_CONF_BATTERYFLG_KEY))?true:false;
		

	}
	
	public void onReceive(Context context, Intent intent) {
		
		if(!mailConfig.isValiable()){
			LogUtils.e(ImmutableValues.MONITOR_RECEIVER_FLG, "mail setting is wrong.");
			return;
		}
		
		if(!NetworkUtils.isOpenNetwork(context)){
			LogUtils.e(ImmutableValues.MONITOR_RECEIVER_FLG, "has not valiable network.");
			return;
		}
		
		String actionName = intent.getAction();
		LogUtils.d(ImmutableValues.MONITOR_RECEIVER_FLG, "Receiver Broadcast action:" + intent.getAction());
		
		String mailSubject = "";
		String mailBody = "";
		
		
		
		if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED) && isPhoneEnable){
			//android.intent.action.PHONE_STATE
			
			String extraState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			
			LogUtils.d(ImmutableValues.MONITOR_RECEIVER_FLG, "Receiver Broadcast action extra_state:" + extraState);
			
			//只有未接来电时，才发送。
			if(TextUtils.isEmpty(preStatus)){
				preStatus = extraState;
				startRingTime = new Date();
			}else if(preStatus.equals(TelephonyManager.EXTRA_STATE_RINGING)
					&& extraState.equals(TelephonyManager.EXTRA_STATE_IDLE)){
				
				long ringTimes = new Date().getTime() - startRingTime.getTime();
				long ringTimesSec = ringTimes / 1000;
				
				String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				String contactName = ImmutableValues.MSG_CONTACT_UNKNOW;
				
				if(TextUtils.isEmpty(incomingNumber)){
					incomingNumber = ImmutableValues.MSG_TELNO_UNKNOW;
				}else{
					contactName = ContactUtils.getContactNameFromPhoneBook(context, incomingNumber);
				}

				mailBody = MailSenderUtils.assembleMailBodyForPhone(incomingNumber, contactName,DateUtils.formateYMDHMS(startRingTime),ringTimesSec);				
				mailSubject = ImmutableValues.MSG_MAIL_SUBJECT_TEL + " - " + DateUtils.formateYMDHMS(new Date());
			
		        preStatus = "";
		        startRingTime = null;
			}
		}else if(actionName.equals(Intents.SMS_RECEIVED_ACTION) && isSmsEnable ){
			//android.provider.Telephony.SMS_RECEIVED
			
			StringBuilder mailBodyBuilder = new StringBuilder();
			
//			SmsMessage[] msgs = Intents.getMessagesFromIntent(intent);
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				for (int i = 0; i < pdus.length; i++) {
					SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) pdus[i]);
					String smsMsgDate = DateUtils.formateYMDHMS(new Date(smsMsg.getTimestampMillis()));
					String smsMsgNumber = smsMsg.getOriginatingAddress();
					String smsMsgContactName = ImmutableValues.MSG_CONTACT_UNKNOW;
					
					if(TextUtils.isEmpty(smsMsgNumber)){
						smsMsgNumber = ImmutableValues.MSG_TELNO_UNKNOW;
					}else{
						smsMsgContactName = ContactUtils.getContactNameFromPhoneBook(context, smsMsgNumber);
					}
					
					String smsMsgBody = smsMsg.getDisplayMessageBody();

					mailBodyBuilder.append(MailSenderUtils.assembleMailBodyForSMS(smsMsgDate,smsMsgContactName, smsMsgNumber, smsMsgBody));
					mailBodyBuilder.append("============================================================");
					
				}
			}
			
			mailBody = mailBodyBuilder.toString();
			mailSubject = ImmutableValues.MSG_MAIL_SUBJECT_SMS + " - " + DateUtils.formateYMDHMS(new Date());

		}else if(actionName.equals(Intent.ACTION_BATTERY_LOW ) && isBatteryEnable){
			//获取当前电量
			int level = intent.getIntExtra("level", 0);
			//电量的总刻度
			int scale = intent.getIntExtra("scale", 100);
			//把它转成百分比
			String percent = (level*100)/scale + "%";
			
			mailBody = MailSenderUtils.assembleMailBodyForBattery(DateUtils.formateYMDHMS(new Date()), percent);
			mailSubject = ImmutableValues.MSG_MAIL_SUBJECT_BATTERY + " - " + DateUtils.formateYMDHMS(new Date());			
		}
		
		if(!TextUtils.isEmpty(mailSubject) && !TextUtils.isEmpty(mailBody)){
			mailConfig.setSubject(mailSubject);
			mailConfig.setMsgBody(mailBody);
			
			Uri.Builder builder = new Uri.Builder();
	        AsyncMailSendTask task = new AsyncMailSendTask(mailConfig);
	        task.execute(builder);
		}	
		
		return;
	}
	
}
