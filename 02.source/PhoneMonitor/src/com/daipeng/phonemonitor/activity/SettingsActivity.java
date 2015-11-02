package com.daipeng.phonemonitor.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.daipeng.phonemonitor.R;
import com.daipeng.phonemonitor.comon.ImmutableValues;
import com.daipeng.phonemonitor.tasks.AsyncMailSendTask;
import com.daipeng.phonemonitor.utils.LogUtils;
import com.daipeng.phonemonitor.utils.LogUtils.LogType;
import com.daipeng.phonemonitor.utils.MailConfig;
import com.daipeng.phonemonitor.utils.PrefsUtils;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity{
	
	private Switch phoneSwitchView;
	private Switch smsSwitchView;
	private Switch batterySwitchView;
	private EditText mailInputView;
	private LinearLayout basicSettingView;
	private Button basicSettingButton;
	private LinearLayout mailSettingView;
	private Button mailSettingButton;
	private EditText mailFromAddressView;
	private EditText mailSmtpHostView;
	private EditText mailSmtpPortView;
	private CheckBox mailSmtpAuthView;
	private CheckBox mailStartTlsView;
	private EditText mailUserNameView;
	private EditText mailUserPwdView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		phoneSwitchView = (Switch) findViewById(R.id.phone_switch);
		smsSwitchView = (Switch) findViewById(R.id.sms_switch);
		batterySwitchView = (Switch) findViewById(R.id.battery_switch);
		mailInputView = (EditText) findViewById(R.id.mail_input);
		basicSettingView = (LinearLayout) findViewById(R.id.basicsettingView);
		basicSettingButton = (Button) findViewById(R.id.basicsetting);
		mailSettingView = (LinearLayout) findViewById(R.id.mailsettingView);
		mailSettingButton = (Button) findViewById(R.id.mailsetting);
		
		mailFromAddressView = (EditText) findViewById(R.id.mail_fromaddress);
		mailSmtpHostView = (EditText) findViewById(R.id.mail_stmphost);
		mailSmtpPortView = (EditText) findViewById(R.id.mail_stmpport);
		mailSmtpAuthView = (CheckBox) findViewById(R.id.mai_smtpauth);
		mailStartTlsView = (CheckBox) findViewById(R.id.mail_starttls);
		mailUserNameView = (EditText) findViewById(R.id.mail_username);
		mailUserPwdView = (EditText) findViewById(R.id.mail_userpwd);
		
		Map<String,String> settings = PrefsUtils.read(this, ImmutableValues.APP_CONF_FILE_NAME);
		String mailAddInput = settings.get(ImmutableValues.APP_CONF_MAILTO_KEY);
		String phoneFlg = settings.get(ImmutableValues.APP_CONF_PHONEFLG_KEY);
		String smsFlg = settings.get(ImmutableValues.APP_CONF_SMSFLG_KEY);
		String batteryFlg = settings.get(ImmutableValues.APP_CONF_BATTERYFLG_KEY);
		String mailFromAddress = settings.get(ImmutableValues.APP_CONF_MAIL_FROMADDRESS_KEY);
		String mailSmtpHost = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPHOST_KEY);
		String mailSmtpPort = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPPORT_KEY);
		String mailSmtpAuth = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPAUTH_KEY);
		String mailStartTls = settings.get(ImmutableValues.APP_CONF_MAIL_STARTTLS_KEY);
		String mailUserName = settings.get(ImmutableValues.APP_CONF_MAIL_USERNAME_KEY);
		String mailUserPwd = settings.get(ImmutableValues.APP_CONF_MAIL_USERPWD_KEY);
		
		boolean isPhoneChecked = false;
		boolean isSmsChecked = false;
		boolean isBatteryChecked = false;
		boolean isSmtpAuthCheked = false;
		boolean isSmtpStartTls = false;
		if(ImmutableValues.FLG_ON.equals(phoneFlg)){
			isPhoneChecked = true;
		}
		if(ImmutableValues.FLG_ON.equals(smsFlg)){
			isSmsChecked = true;
		}
		if(ImmutableValues.FLG_ON.equals(batteryFlg)){
			isBatteryChecked = true;
		}
		if(ImmutableValues.TRUE.equals(mailSmtpAuth)){
			isSmtpAuthCheked = true;
		}
		if(ImmutableValues.TRUE.equals(mailStartTls)){
			isSmtpStartTls = true;
		}
		
		
		phoneSwitchView.setChecked(isPhoneChecked);
		smsSwitchView.setChecked(isSmsChecked);
		batterySwitchView.setChecked(isBatteryChecked);
		mailInputView.setText(mailAddInput);
		mailFromAddressView.setText(mailFromAddress);
		mailSmtpHostView.setText(mailSmtpHost);
		mailSmtpPortView.setText(mailSmtpPort);
		mailSmtpAuthView.setChecked(isSmtpAuthCheked);
		mailStartTlsView.setChecked(isSmtpStartTls);
		mailUserNameView.setText(mailUserName);
		mailUserPwdView.setText(mailUserPwd);
		
		onBasicSetting(null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//do not display menu
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// do nothing
		return true;
	}
	
	/**
	 * back
	 * @param source
	 */
	public void onBack(View source){
		this.finish();
	}
	
	/**
	 * back
	 * @param source
	 */
	public void onSave(View source){
		
		String mailAddInput = mailInputView.getText().toString();
		String phoneFlg = ImmutableValues.FLG_OFF;
		String smsFlg = ImmutableValues.FLG_OFF;
		String batteryFlg = ImmutableValues.FLG_OFF;
		
		String mailFromAddress = mailFromAddressView.getText().toString();
		String mailSmtpHost = mailSmtpHostView.getText().toString();
		String mailSmtpPort = mailSmtpPortView.getText().toString();
		String mailSmtpAuth = ImmutableValues.FALSE;
		String mailStartTls = ImmutableValues.FALSE;
		String mailUserName = mailUserNameView.getText().toString();
		String mailUserPwd = mailUserPwdView.getText().toString();				
		
		
		
		if(phoneSwitchView.isChecked()){
			phoneFlg = ImmutableValues.FLG_ON;
		}
		if(smsSwitchView.isChecked()){
			smsFlg = ImmutableValues.FLG_ON;
		}
		if(batterySwitchView.isChecked()){
			batteryFlg = ImmutableValues.FLG_ON;
		}
		if(mailSmtpAuthView.isChecked()){
			mailSmtpAuth = ImmutableValues.TRUE;
		}
		if(mailStartTlsView.isChecked()){
			mailStartTls = ImmutableValues.TRUE;
		}
		
		
		Map<String,String> settings = new HashMap<String, String>();
		settings.put(ImmutableValues.APP_CONF_MAILTO_KEY, mailAddInput);
		settings.put(ImmutableValues.APP_CONF_PHONEFLG_KEY, phoneFlg);
		settings.put(ImmutableValues.APP_CONF_SMSFLG_KEY, smsFlg);
		settings.put(ImmutableValues.APP_CONF_BATTERYFLG_KEY, batteryFlg);
		settings.put(ImmutableValues.APP_CONF_MAIL_FROMADDRESS_KEY,mailFromAddress);
		settings.put(ImmutableValues.APP_CONF_MAIL_SMTPHOST_KEY,mailSmtpHost);
		settings.put(ImmutableValues.APP_CONF_MAIL_SMTPPORT_KEY,mailSmtpPort);
		settings.put(ImmutableValues.APP_CONF_MAIL_SMTPAUTH_KEY,mailSmtpAuth);
		settings.put(ImmutableValues.APP_CONF_MAIL_STARTTLS_KEY,mailStartTls);
		settings.put(ImmutableValues.APP_CONF_MAIL_USERNAME_KEY,mailUserName);
		settings.put(ImmutableValues.APP_CONF_MAIL_USERPWD_KEY,mailUserPwd);
		
		PrefsUtils.save(this, settings, ImmutableValues.APP_CONF_FILE_NAME);
		
		LogUtils.log(LogType.INFO,ImmutableValues.SETTING_ACTIVE_TAG, "program setting has been changed...");
		Toast.makeText(this, "Setting Change Successful", Toast.LENGTH_LONG).show();;
	}
	

	public void onBasicSetting(View source){
		basicSettingButton.setEnabled(false);
		mailSettingButton.setEnabled(true);
		basicSettingView.setVisibility(View.VISIBLE);
		mailSettingView.setVisibility(View.INVISIBLE);
	}
	
	public void onMailSetting(View source){
		basicSettingButton.setEnabled(true);
		mailSettingButton.setEnabled(false);
		basicSettingView.setVisibility(View.INVISIBLE);
		mailSettingView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Mail Test
	 * @param source
	 */
	public void onMailTest(View source){
		LogUtils.log(LogType.DEBUG,ImmutableValues.SETTING_ACTIVE_TAG, "onMailTest start...");
		MailConfig mailConfig = MailConfig.loadMailConfig(this);
		mailConfig.setSubject("[Phone Monitor] Test" + new Date());
		mailConfig.setMsgBody("Do Test");
		
		Uri.Builder builder = new Uri.Builder();
        AsyncMailSendTask task = new AsyncMailSendTask(mailConfig);
        task.execute(builder);
		
        Toast.makeText(this, "≤‚ ‘” º˛“—∑¢ÀÕ", Toast.LENGTH_LONG).show();
		LogUtils.log(LogType.DEBUG,ImmutableValues.SETTING_ACTIVE_TAG, "onMailTest end...");
	}
}
