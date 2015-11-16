package com.daipeng.phonemonitor.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.daipeng.phonemonitor.R;
import com.daipeng.phonemonitor.comon.ImmutableValues;
import com.daipeng.phonemonitor.tasks.AsyncMailSendTask;
import com.daipeng.phonemonitor.utils.DateUtils;
import com.daipeng.phonemonitor.utils.LogUtils;
import com.daipeng.phonemonitor.utils.LogUtils.LogType;
import com.daipeng.phonemonitor.utils.MailConfig;
import com.daipeng.phonemonitor.utils.MailConfig.MailConfigKbn;
import com.daipeng.phonemonitor.utils.MailContent;
import com.daipeng.phonemonitor.utils.PrefsUtils;
import com.daipeng.phonemonitor.utils.StringUtils;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity implements ImmutableValues{
	
	private Switch phoneSwitchView;
	private Switch smsSwitchView;
	private Switch batterySwitchView;
	private LinearLayout basicSettingView;
	private Button basicSettingButton;
	private LinearLayout mailSettingView;
	private Button mailSettingButton;

	private EditText mailInputView;
	private EditText mailFromAddressView;
	private EditText mailSmtpHostView;
	private EditText mailSmtpPortView;
	private RadioGroup mailEncryptView;
	private EditText mailUserNameView;
	private EditText mailUserPwdView;
	
	private EditText mailInputViewSpare1;
	private EditText mailFromAddressViewSpare1;
	private EditText mailSmtpHostViewSpare1;
	private EditText mailSmtpPortViewSpare1;
	private RadioGroup mailEncryptViewSpare1;
	private EditText mailUserNameViewSpare1;
	private EditText mailUserPwdViewSpare1;
	
	private EditText mailInputViewSpare2;
	private EditText mailFromAddressViewSpare2;
	private EditText mailSmtpHostViewSpare2;
	private EditText mailSmtpPortViewSpare2;
	private RadioGroup mailEncryptViewSpare2;
	private EditText mailUserNameViewSpare2;
	private EditText mailUserPwdViewSpare2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		Map<String,String> settings = PrefsUtils.read(this, APP_CONF_FILE_NAME);
		
		initActivity(settings);
		
		initBasicSetting(settings);
		
		initMailSetting(settings);
		initMailSpare1Setting(settings);
		initMailSpare2Setting(settings);
		
		onBasicSetting(null);
		
	}

	private void initMailSpare2Setting(Map<String, String> settings) {
		mailInputViewSpare2 = (EditText) findViewById(R.id.mail_input_2);
		mailFromAddressViewSpare2 = (EditText) findViewById(R.id.mail_fromaddress_2);
		mailSmtpHostViewSpare2 = (EditText) findViewById(R.id.mail_stmphost_2);
		mailSmtpPortViewSpare2 = (EditText) findViewById(R.id.mail_stmpport_2);
		mailEncryptViewSpare2 = (RadioGroup) findViewById(R.id.mail_encrtpy_2);
		mailUserNameViewSpare2 = (EditText) findViewById(R.id.mail_username_2);
		mailUserPwdViewSpare2 = (EditText) findViewById(R.id.mail_userpwd_2);
		
		String mailAddInput = settings.get(APP_CONF_MAILTO_SPARE_2_KEY);
		String mailFromAddress = settings.get(APP_CONF_MAIL_FROMADDRESS_SPARE_2_KEY);
		String mailSmtpHost = settings.get(APP_CONF_MAIL_SMTPHOST_SPARE_2_KEY);
		String mailSmtpPort = settings.get(APP_CONF_MAIL_SMTPPORT_SPARE_2_KEY);
		String mailSmtpEncrypt = settings.get(APP_CONF_MAIL_ENCRYPT_SPARE_2_KEY);
		String mailUserName = settings.get(APP_CONF_MAIL_USERNAME_SPARE_2_KEY);
		String mailUserPwd = settings.get(APP_CONF_MAIL_USERPWD_SPARE_2_KEY);
		
		int mailEncryptId = 0;
		if(APP_CONF_MAIL_ENCRYPT_TLS.equals(mailSmtpEncrypt)){
			mailEncryptId = R.id.mail_encrtpy_tls_2;
		}else if(APP_CONF_MAIL_ENCRYPT_SSL.equals(mailSmtpEncrypt)){
			mailEncryptId = R.id.mail_encrtpy_ssl_2;
		}
		
		mailInputViewSpare2.setText(mailAddInput);
		mailFromAddressViewSpare2.setText(mailFromAddress);
		mailSmtpHostViewSpare2.setText(mailSmtpHost);
		mailSmtpPortViewSpare2.setText(mailSmtpPort);
		mailEncryptViewSpare2.check(mailEncryptId);
		mailUserNameViewSpare2.setText(mailUserName);
		mailUserPwdViewSpare2.setText(mailUserPwd);
	}

	private void initMailSpare1Setting(Map<String, String> settings) {
		mailInputViewSpare1 = (EditText) findViewById(R.id.mail_input_1);
		mailFromAddressViewSpare1 = (EditText) findViewById(R.id.mail_fromaddress_1);
		mailSmtpHostViewSpare1 = (EditText) findViewById(R.id.mail_stmphost_1);
		mailSmtpPortViewSpare1 = (EditText) findViewById(R.id.mail_stmpport_1);
		mailEncryptViewSpare1 = (RadioGroup) findViewById(R.id.mail_encrtpy_1);
		mailUserNameViewSpare1 = (EditText) findViewById(R.id.mail_username_1);
		mailUserPwdViewSpare1 = (EditText) findViewById(R.id.mail_userpwd_1);
		
		String mailAddInput = settings.get(APP_CONF_MAILTO_SPARE_1_KEY);
		String mailFromAddress = settings.get(APP_CONF_MAIL_FROMADDRESS_SPARE_1_KEY);
		String mailSmtpHost = settings.get(APP_CONF_MAIL_SMTPHOST_SPARE_1_KEY);
		String mailSmtpPort = settings.get(APP_CONF_MAIL_SMTPPORT_SPARE_1_KEY);
		String mailSmtpEncrypt = settings.get(APP_CONF_MAIL_ENCRYPT_SPARE_1_KEY);
		String mailUserName = settings.get(APP_CONF_MAIL_USERNAME_SPARE_1_KEY);
		String mailUserPwd = settings.get(APP_CONF_MAIL_USERPWD_SPARE_1_KEY);
		
		int mailEncryptId = 0;
		if(APP_CONF_MAIL_ENCRYPT_TLS.equals(mailSmtpEncrypt)){
			mailEncryptId = R.id.mail_encrtpy_tls_1;
		}else if(APP_CONF_MAIL_ENCRYPT_SSL.equals(mailSmtpEncrypt)){
			mailEncryptId = R.id.mail_encrtpy_ssl_1;
		}
		
		mailInputViewSpare1.setText(mailAddInput);
		mailFromAddressViewSpare1.setText(mailFromAddress);
		mailSmtpHostViewSpare1.setText(mailSmtpHost);
		mailSmtpPortViewSpare1.setText(mailSmtpPort);
		mailEncryptViewSpare1.check(mailEncryptId);
		mailUserNameViewSpare1.setText(mailUserName);
		mailUserPwdViewSpare1.setText(mailUserPwd);
	}

	private void initMailSetting(Map<String, String> settings) {
		mailInputView = (EditText) findViewById(R.id.mail_input);
		mailFromAddressView = (EditText) findViewById(R.id.mail_fromaddress);
		mailSmtpHostView = (EditText) findViewById(R.id.mail_stmphost);
		mailSmtpPortView = (EditText) findViewById(R.id.mail_stmpport);
		mailEncryptView = (RadioGroup) findViewById(R.id.mail_encrtpy);
		mailUserNameView = (EditText) findViewById(R.id.mail_username);
		mailUserPwdView = (EditText) findViewById(R.id.mail_userpwd);
		
		String mailAddInput = settings.get(APP_CONF_MAILTO_KEY);
		String mailFromAddress = settings.get(APP_CONF_MAIL_FROMADDRESS_KEY);
		String mailSmtpHost = settings.get(APP_CONF_MAIL_SMTPHOST_KEY);
		String mailSmtpPort = settings.get(APP_CONF_MAIL_SMTPPORT_KEY);
		String mailSmtpEncrypt = settings.get(APP_CONF_MAIL_ENCRYPT_KEY);
		String mailUserName = settings.get(APP_CONF_MAIL_USERNAME_KEY);
		String mailUserPwd = settings.get(APP_CONF_MAIL_USERPWD_KEY);
		
		int mailEncryptId = 0;
		if(APP_CONF_MAIL_ENCRYPT_TLS.equals(mailSmtpEncrypt)){
			mailEncryptId = R.id.mail_encrtpy_tls;
		}else if(APP_CONF_MAIL_ENCRYPT_SSL.equals(mailSmtpEncrypt)){
			mailEncryptId = R.id.mail_encrtpy_ssl;
		}
		
		mailInputView.setText(mailAddInput);
		mailFromAddressView.setText(mailFromAddress);
		mailSmtpHostView.setText(mailSmtpHost);
		mailSmtpPortView.setText(mailSmtpPort);
		mailEncryptView.check(mailEncryptId);
		mailUserNameView.setText(mailUserName);
		mailUserPwdView.setText(mailUserPwd);
	}

	private void initBasicSetting(Map<String, String> settings) {
		phoneSwitchView = (Switch) findViewById(R.id.phone_switch);
		smsSwitchView = (Switch) findViewById(R.id.sms_switch);
		batterySwitchView = (Switch) findViewById(R.id.battery_switch);
		
		String phoneFlg = settings.get(APP_CONF_PHONEFLG_KEY);
		String smsFlg = settings.get(APP_CONF_SMSFLG_KEY);
		String batteryFlg = settings.get(APP_CONF_BATTERYFLG_KEY);
		
		boolean isPhoneChecked = false;
		boolean isSmsChecked = false;
		boolean isBatteryChecked = false;
		if(FLG_ON.equals(phoneFlg)){
			isPhoneChecked = true;
		}
		if(FLG_ON.equals(smsFlg)){
			isSmsChecked = true;
		}
		if(FLG_ON.equals(batteryFlg)){
			isBatteryChecked = true;
		}
		
		phoneSwitchView.setChecked(isPhoneChecked);
		smsSwitchView.setChecked(isSmsChecked);
		batterySwitchView.setChecked(isBatteryChecked);
	}

	private void initActivity(Map<String, String> settings) {
		basicSettingView = (LinearLayout) findViewById(R.id.basicsettingView);
		basicSettingButton = (Button) findViewById(R.id.basicsetting);
		mailSettingView = (LinearLayout) findViewById(R.id.mailsettingView);
		mailSettingButton = (Button) findViewById(R.id.mailsetting);
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
		
		Map<String,String> settings = new HashMap<String, String>();
		
		getBaseicSettingInput(settings);
		
		getMailMainSettingInput(settings);
		
		getMailSpare1SettingInput(settings);
		
		getMailSpare2SettingInput(settings);
		
		PrefsUtils.save(this, settings, APP_CONF_FILE_NAME);
		
		LogUtils.log(LogType.INFO,SETTING_ACTIVE_TAG, "program setting has been changed...");
		Toast.makeText(this, "Setting Change Successful", Toast.LENGTH_LONG).show();;
	}

	private void getMailSpare2SettingInput(Map<String, String> settings) {
		String mailAddrInput = mailInputViewSpare2.getText().toString();
		String mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
		String mailFromAddress = mailFromAddressViewSpare2.getText().toString();
		String mailSmtpHost = mailSmtpHostViewSpare2.getText().toString();
		String mailSmtpPort = mailSmtpPortViewSpare2.getText().toString();
		String mailSmtpAuth = TRUE;
		String mailUserName = mailUserNameViewSpare2.getText().toString();
		String mailUserPwd = mailUserPwdViewSpare2.getText().toString();				
		int mailEncryptId = mailEncryptViewSpare2.getCheckedRadioButtonId();

		if(mailEncryptId == R.id.mail_encrtpy_tls_2){
			mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
		}else if(mailEncryptId == R.id.mail_encrtpy_ssl_2){
			mailEncryptType = APP_CONF_MAIL_ENCRYPT_SSL;
		}
		
		settings.put(APP_CONF_MAILTO_SPARE_2_KEY, mailAddrInput);
		settings.put(APP_CONF_MAIL_FROMADDRESS_SPARE_2_KEY,mailFromAddress);
		settings.put(APP_CONF_MAIL_SMTPHOST_SPARE_2_KEY,mailSmtpHost);
		settings.put(APP_CONF_MAIL_SMTPPORT_SPARE_2_KEY,mailSmtpPort);
		settings.put(APP_CONF_MAIL_SMTPAUTH_SPARE_2_KEY,mailSmtpAuth);
		settings.put(APP_CONF_MAIL_USERNAME_SPARE_2_KEY,mailUserName);
		settings.put(APP_CONF_MAIL_USERPWD_SPARE_2_KEY,mailUserPwd);
		settings.put(APP_CONF_MAIL_ENCRYPT_SPARE_2_KEY,mailEncryptType);
		
	}

	private void getMailSpare1SettingInput(Map<String, String> settings) {
		String mailAddrInput = mailInputViewSpare1.getText().toString();
		String mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
		String mailFromAddress = mailFromAddressViewSpare1.getText().toString();
		String mailSmtpHost = mailSmtpHostViewSpare1.getText().toString();
		String mailSmtpPort = mailSmtpPortViewSpare1.getText().toString();
		String mailSmtpAuth = TRUE;
		String mailUserName = mailUserNameViewSpare1.getText().toString();
		String mailUserPwd = mailUserPwdViewSpare1.getText().toString();				
		int mailEncryptId = mailEncryptViewSpare1.getCheckedRadioButtonId();

		if(mailEncryptId == R.id.mail_encrtpy_tls_1){
			mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
		}else if(mailEncryptId == R.id.mail_encrtpy_ssl_1){
			mailEncryptType = APP_CONF_MAIL_ENCRYPT_SSL;
		}
		
		settings.put(APP_CONF_MAILTO_SPARE_1_KEY, mailAddrInput);
		settings.put(APP_CONF_MAIL_FROMADDRESS_SPARE_1_KEY,mailFromAddress);
		settings.put(APP_CONF_MAIL_SMTPHOST_SPARE_1_KEY,mailSmtpHost);
		settings.put(APP_CONF_MAIL_SMTPPORT_SPARE_1_KEY,mailSmtpPort);
		settings.put(APP_CONF_MAIL_SMTPAUTH_SPARE_1_KEY,mailSmtpAuth);
		settings.put(APP_CONF_MAIL_USERNAME_SPARE_1_KEY,mailUserName);
		settings.put(APP_CONF_MAIL_USERPWD_SPARE_1_KEY,mailUserPwd);
		settings.put(APP_CONF_MAIL_ENCRYPT_SPARE_1_KEY,mailEncryptType);
		
	}

	private void getMailMainSettingInput(Map<String, String> settings) {
		String mailAddrInput = mailInputView.getText().toString();
		String mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
		String mailFromAddress = mailFromAddressView.getText().toString();
		String mailSmtpHost = mailSmtpHostView.getText().toString();
		String mailSmtpPort = mailSmtpPortView.getText().toString();
		String mailSmtpAuth = TRUE;
		String mailUserName = mailUserNameView.getText().toString();
		String mailUserPwd = mailUserPwdView.getText().toString();				
		int mailEncryptId = mailEncryptView.getCheckedRadioButtonId();

		if(mailEncryptId == R.id.mail_encrtpy_tls){
			mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
		}else if(mailEncryptId == R.id.mail_encrtpy_ssl){
			mailEncryptType = APP_CONF_MAIL_ENCRYPT_SSL;
		}
		
		settings.put(APP_CONF_MAILTO_KEY, mailAddrInput);
		settings.put(APP_CONF_MAIL_FROMADDRESS_KEY,mailFromAddress);
		settings.put(APP_CONF_MAIL_SMTPHOST_KEY,mailSmtpHost);
		settings.put(APP_CONF_MAIL_SMTPPORT_KEY,mailSmtpPort);
		settings.put(APP_CONF_MAIL_SMTPAUTH_KEY,mailSmtpAuth);
		settings.put(APP_CONF_MAIL_USERNAME_KEY,mailUserName);
		settings.put(APP_CONF_MAIL_USERPWD_KEY,mailUserPwd);
		settings.put(APP_CONF_MAIL_ENCRYPT_KEY,mailEncryptType);
		
	}

	private void getBaseicSettingInput(Map<String, String> settings) {
		String phoneFlg = FLG_OFF;
		String smsFlg = FLG_OFF;
		String batteryFlg = FLG_OFF;
		
		if(phoneSwitchView.isChecked()){
			phoneFlg = FLG_ON;
		}
		if(smsSwitchView.isChecked()){
			smsFlg = FLG_ON;
		}
		if(batterySwitchView.isChecked()){
			batteryFlg = FLG_ON;
		}
		
		settings.put(APP_CONF_PHONEFLG_KEY, phoneFlg);
		settings.put(APP_CONF_SMSFLG_KEY, smsFlg);
		settings.put(APP_CONF_BATTERYFLG_KEY, batteryFlg);
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
		LogUtils.log(LogType.DEBUG,SETTING_ACTIVE_TAG, "onMailTest start...");
		MailConfig mailConfig = new MailConfig();

		String mailAddrInput = "";
		String mailFromAddress = "";
		String mailSmtpHost = "";
		String mailSmtpPort = "";
		String mailSmtpAuth = TRUE;
		String mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
		int mailEncryptId = 0;
		String mailUserName = "";
		String mailUserPwd = "";	
		MailConfigKbn mailConfigKbn = null;
		
		switch (source.getId()) {
			case R.id.mailTest:
				mailAddrInput = mailInputView.getText().toString();
				mailFromAddress = mailFromAddressView.getText().toString();
				mailSmtpHost = mailSmtpHostView.getText().toString();
				mailSmtpPort = mailSmtpPortView.getText().toString();
				mailEncryptId = mailEncryptView.getCheckedRadioButtonId();
				mailUserName = mailUserNameView.getText().toString();
				mailUserPwd = mailUserPwdView.getText().toString();
				mailConfigKbn = MailConfigKbn.MAIN;
				if(mailEncryptId == R.id.mail_encrtpy_tls){
					mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
				}else if(mailEncryptId == R.id.mail_encrtpy_ssl){
					mailEncryptType = APP_CONF_MAIL_ENCRYPT_SSL;
				}
				break;
			case R.id.mailTest_1:
				mailAddrInput = mailInputViewSpare1.getText().toString();
				mailFromAddress = mailFromAddressViewSpare1.getText().toString();
				mailSmtpHost = mailSmtpHostViewSpare1.getText().toString();
				mailSmtpPort = mailSmtpPortViewSpare1.getText().toString();
				mailEncryptId = mailEncryptViewSpare1.getCheckedRadioButtonId();
				mailUserName = mailUserNameViewSpare1.getText().toString();
				mailUserPwd = mailUserPwdViewSpare1.getText().toString();
				mailConfigKbn = MailConfigKbn.SPARE1;
				if(mailEncryptId == R.id.mail_encrtpy_tls_1){
					mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
				}else if(mailEncryptId == R.id.mail_encrtpy_ssl_1){
					mailEncryptType = APP_CONF_MAIL_ENCRYPT_SSL;
				}
				break;
			case R.id.mailTest_2:
				mailAddrInput = mailInputViewSpare2.getText().toString();
				mailFromAddress = mailFromAddressViewSpare2.getText().toString();
				mailSmtpHost = mailSmtpHostViewSpare2.getText().toString();
				mailSmtpPort = mailSmtpPortViewSpare2.getText().toString();
				mailEncryptId = mailEncryptViewSpare2.getCheckedRadioButtonId();
				mailUserName = mailUserNameViewSpare2.getText().toString();
				mailUserPwd = mailUserPwdViewSpare2.getText().toString();
				mailConfigKbn = MailConfigKbn.SPARE2;
				if(mailEncryptId == R.id.mail_encrtpy_tls_2){
					mailEncryptType = APP_CONF_MAIL_ENCRYPT_TLS;
				}else if(mailEncryptId == R.id.mail_encrtpy_ssl_2){
					mailEncryptType = APP_CONF_MAIL_ENCRYPT_SSL;
				}
				break;	
			default:
				break;
		}
		
		mailConfig.setToAddress(StringUtils.strToList(mailAddrInput));
		mailConfig.setSmtpHost(mailSmtpHost);
		mailConfig.setSmtpPort(mailSmtpPort);
		mailConfig.setSmtpEncryptType(mailEncryptType);
		mailConfig.setSmtpAuth(mailSmtpAuth);
		mailConfig.setUserName(mailUserName);
		mailConfig.setUserPassword(mailUserPwd);
		mailConfig.setFromAddress(mailFromAddress);
		mailConfig.setMailConfigKbn(mailConfigKbn);
		
		MailContent mailContent = new MailContent();
		mailContent.setSubject("[Phone Monitor] Test" + DateUtils.formateYMDHMSS(new Date()));
		mailContent.setMsgBody("Do Test From : " + mailConfigKbn.getName());
		
		Uri.Builder builder = new Uri.Builder();
        AsyncMailSendTask task = new AsyncMailSendTask(mailConfig,mailContent);
        task.execute(builder);
		
        Toast.makeText(this, "≤‚ ‘” º˛“—∑¢ÀÕ", Toast.LENGTH_LONG).show();
		LogUtils.log(LogType.DEBUG,SETTING_ACTIVE_TAG, "onMailTest end...");
	}
}
