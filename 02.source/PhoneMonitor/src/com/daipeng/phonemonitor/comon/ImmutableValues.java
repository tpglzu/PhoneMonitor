package com.daipeng.phonemonitor.comon;

public class ImmutableValues {
	public static final String MAIN_ACTIVE_TAG = "MainActivity";
	public static final String SETTING_ACTIVE_TAG = "SettingsActivity";
	public static final String MONITOR_RECEIVER_FLG = "MonitorReceiver";
	public static final String MONITOR_SERVICE_TAG = "MonitorService";
	public static final String MAIL_SEND_TASK_TAG = "AsyncMailSendTask";
	public static final String MAIL_SEND_TAG = "MailSendUtils";
	public static final String FLG_ON = "on";
	public static final String FLG_OFF = "off";
	
    //configuration file name
	public static final String APP_CONF_FILE_NAME = "MONITOR_CONF";
	//configuration key for mail to
	public static final String APP_CONF_MAILTO_KEY = "MAIL_TO";
	//default configuration value for mail to
	public static final String APP_CONF_MAILTO_VALUE = "tangpenggui@gmail.com";
	public static final String APP_CONF_PHONEFLG_KEY = "PHONE_FLG";
	public static final String APP_CONF_SMSFLG_KEY = "SMS_FLG";
	public static final String APP_CONF_BATTERYFLG_KEY = "BATTERY_FLG";
	public static final String APP_CONF_MAIL_SMTPHOST_KEY = "MAIL_SMTP_HOST";
	public static final String APP_CONF_MAIL_SMTPPORT_KEY = "MAIL_SMTP_PORT";
	public static final String APP_CONF_MAIL_SMTPAUTH_KEY = "MAIL_SMTP_AUTH";
	public static final String APP_CONF_MAIL_FROMADDRESS_KEY = "MAIL_SMTP_FROMADDRESS";
	public static final String APP_CONF_MAIL_STARTTLS_KEY = "MAIL_SMTP_STARTTLS";
	public static final String APP_CONF_MAIL_USERNAME_KEY = "MAIL_SMTP_USERNAME";
	public static final String APP_CONF_MAIL_USERPWD_KEY = "MAIL_SMTP_USERPWD";
	// ,
	public static final String COMMA = ",";
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static final String MSG_TELNO_UNKNOW = "unknow";
	public static final String MSG_CONTACT_UNKNOW = "未知联系人";
	public static final String MSG_MAIL_SUBJECT_TEL = "【新来电提醒】";//FIXME tang_penggui use strings resource
	public static final String MSG_MAIL_SUBJECT_SMS = "【新短信提醒】";//FIXME tang_penggui use strings resource
	public static final String MSG_MAIL_SUBJECT_BATTERY = "【电池电量提醒】";//FIXME tang_penggui use strings resource
	
}
