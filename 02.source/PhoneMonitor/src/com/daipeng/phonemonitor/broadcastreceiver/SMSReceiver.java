package com.daipeng.phonemonitor.broadcastreceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;

/**
 * FIXME from net download, need confirm
 * ling:http://blog.csdn.net/mad1989/article/details/22426415
 * @author tang_penggui
 *
 */
public class SMSReceiver extends BroadcastReceiver {
	
	private SmsObserver smsObserver;

	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.app_login);
		smsObserver = new SmsObserver(smsHandler);
		getContentResolver().registerContentObserver(SMS_INBOX, true,
				smsObserver);

	}
	public Handler smsHandler = new Handler() {
		//这里可以进行回调的操作
		//TODO

	};
	class SmsObserver extends ContentObserver {

		public SmsObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			//每当有新短信到来时，使用我们获取短消息的方法
			getSmsFromPhone();
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage msg = null;
		if (null != bundle) {
			Object[] smsObj = (Object[]) bundle.get("pdus");
			for (Object object : smsObj) {
				msg = SmsMessage.createFromPdu((byte[]) object);
    		Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
				System.out.println("number:" + msg.getOriginatingAddress()
				+ "   body:" + msg.getDisplayMessageBody() + "  time:"
						+ msg.getTimestampMillis());
				
				//在这里写自己的逻辑
				if (msg.getOriginatingAddress().equals("10086")) {
					//TODO
					
				}
				
			}
		}
	}
	
	private Uri SMS_INBOX = Uri.parse("content://sms/");
	public void getSmsFromPhone() {
		ContentResolver cr = getContentResolver();
		String[] projection = new String[] { "body" };//"_id", "address", "person",, "date", "type
		String where = " address = '1066321332' AND date >  "
				+ (System.currentTimeMillis() - 10 * 60 * 1000);
		Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
		if (null == cur)
			return;
		if (cur.moveToNext()) {
			String number = cur.getString(cur.getColumnIndex("address"));//手机号
			String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
			String body = cur.getString(cur.getColumnIndex("body"));
			//这里我是要获取自己短信服务号码中的验证码~~
			Pattern pattern = Pattern.compile(" [a-zA-Z0-9]{10}");
			Matcher matcher = pattern.matcher(body);
			if (matcher.find()) {
				String res = matcher.group().substring(1, 11);
//				mobileText.setText(res);
			}
		}
	}
	private ContentResolver getContentResolver() {
		// TODO Auto-generated method stub
		return null;
	}


}
