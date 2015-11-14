package com.daipeng.phonemonitor.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.daipeng.phonemonitor.comon.ImmutableValues;

import android.content.Context;
import android.text.TextUtils;

/**
 * configuration bean for sending mail
 * @author tang_penggui
 *
 */
public class MailConfig {
	
	public enum MailConfigKbn{
		NONE("none"),
		MAIN("main"),
		SPARE1("spare1"),
		SPARE2("spare2");
		
		String name;
		private MailConfigKbn(String name) {
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
	}
	
	public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
	public static final String KEY_MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String KEY_MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String KEY_MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String KEY_MAIL_SMTP_STARTTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String KEY_MAIL_SMTP_DEBUG = "mail.smtp.debug";
	
	public static final String KEY_MAIL_SMTP_SSL_FACTORYCLASS = "mail.smtp.socketFactory.class";
	public static final String KEY_MAIL_SMTP_SSL_FALLBACK = "mail.smtp.socketFactory.fallback";
	public static final String KEY_MAIL_SMTP_SSL_FACTORYPORT = "mail.smtp.socketFactory.port";
	public static final String VALUE_MAIL_SMTP_SSL_FACTORYCLASS = "javax.net.ssl.SSLSocketFactory";
	public static final String VALUE_MAIL_SMTP_SSL_FALLBACK = "false";
	
	private String fromAddress;
	private String userName;
	private String userPassword;
	private List<String> toAddress;
	private List<String> ccAddress;
	
	private String smtpHost;
	private String smtpPort;
	private String smtpAuth;
	private String smtpEncryptType;
	
	private MailConfigKbn mailConfigKbn = MailConfigKbn.NONE;//
	
	public MailConfig(){}
	
	public static MailConfig loadMailConfig(Context context){
		Map<String,String> settings = PrefsUtils.read(context, ImmutableValues.APP_CONF_FILE_NAME);
		
		return loadMailConfig(settings);
	}
	
	public static List<MailConfig> loadMailConfigList(Context context){
		Map<String,String> settings = PrefsUtils.read(context, ImmutableValues.APP_CONF_FILE_NAME);
		
		return loadMailConfigList(settings);
	}
	
	public static MailConfig loadMailConfig(Map<String, String> settings) {
		MailConfig config = new MailConfig();
		
		String mailFromAddress = settings.get(ImmutableValues.APP_CONF_MAIL_FROMADDRESS_KEY);
		String mailSmtpHost = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPHOST_KEY);
		String mailSmtpPort = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPPORT_KEY);
		String mailSmtpAuth = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPAUTH_KEY);
		String mailEncrypt = settings.get(ImmutableValues.APP_CONF_MAIL_ENCRYPT_KEY);
		String mailUserName = settings.get(ImmutableValues.APP_CONF_MAIL_USERNAME_KEY);
		String mailUserPwd = settings.get(ImmutableValues.APP_CONF_MAIL_USERPWD_KEY);
		String mailTo = settings.get(ImmutableValues.APP_CONF_MAILTO_KEY);
		List<String> toAddress = StringUtils.strToList(mailTo);
		
		config.setSmtpHost(mailSmtpHost);
		config.setSmtpPort(mailSmtpPort);
		config.setSmtpAuth(mailSmtpAuth);
		config.setFromAddress(mailFromAddress);
		config.setUserName(mailUserName);
		config.setUserPassword(mailUserPwd);
		config.setToAddress(toAddress);
		config.setSmtpEncryptType(mailEncrypt);
		config.setMailConfigKbn(MailConfigKbn.MAIN);
		return config;
	}
	
	public static List<MailConfig> loadMailConfigList(Map<String, String> settings) {
		List<MailConfig>  configs = new ArrayList<MailConfig>();
		//add mail config main
		String mailFromAddress = settings.get(ImmutableValues.APP_CONF_MAIL_FROMADDRESS_KEY);
		String mailSmtpHost = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPHOST_KEY);
		String mailSmtpPort = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPPORT_KEY);
		String mailSmtpAuth = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPAUTH_KEY);
		String mailEncrypt = settings.get(ImmutableValues.APP_CONF_MAIL_ENCRYPT_KEY);
		String mailUserName = settings.get(ImmutableValues.APP_CONF_MAIL_USERNAME_KEY);
		String mailUserPwd = settings.get(ImmutableValues.APP_CONF_MAIL_USERPWD_KEY);
		String mailTo = settings.get(ImmutableValues.APP_CONF_MAILTO_KEY);
		List<String> toAddress = StringUtils.strToList(mailTo);
		
		MailConfig configMain = new MailConfig();
		configMain.setSmtpHost(mailSmtpHost);
		configMain.setSmtpPort(mailSmtpPort);
		configMain.setSmtpAuth(mailSmtpAuth);
		configMain.setFromAddress(mailFromAddress);
		configMain.setUserName(mailUserName);
		configMain.setUserPassword(mailUserPwd);
		configMain.setToAddress(toAddress);
		configMain.setSmtpEncryptType(mailEncrypt);
		configMain.setMailConfigKbn(MailConfigKbn.MAIN);
		
		if(configMain.isValiable()){
			configs.add(configMain);
		}
		
		//add mail config spare 1
		mailFromAddress = settings.get(ImmutableValues.APP_CONF_MAIL_FROMADDRESS_SPARE_1_KEY);
		mailSmtpHost = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPHOST_SPARE_1_KEY);
		mailSmtpPort = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPPORT_SPARE_1_KEY);
		mailSmtpAuth = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPAUTH_SPARE_1_KEY);
		mailEncrypt = settings.get(ImmutableValues.APP_CONF_MAIL_ENCRYPT_SPARE_1_KEY);
		mailUserName = settings.get(ImmutableValues.APP_CONF_MAIL_USERNAME_SPARE_1_KEY);
		mailUserPwd = settings.get(ImmutableValues.APP_CONF_MAIL_USERPWD_SPARE_1_KEY);
		mailTo = settings.get(ImmutableValues.APP_CONF_MAILTO_SPARE_1_KEY);
		toAddress = StringUtils.strToList(mailTo);
		
		MailConfig configSpare1 = new MailConfig();
		configSpare1.setSmtpHost(mailSmtpHost);
		configSpare1.setSmtpPort(mailSmtpPort);
		configSpare1.setSmtpAuth(mailSmtpAuth);
		configSpare1.setFromAddress(mailFromAddress);
		configSpare1.setUserName(mailUserName);
		configSpare1.setUserPassword(mailUserPwd);
		configSpare1.setToAddress(toAddress);
		configSpare1.setSmtpEncryptType(mailEncrypt);
		configSpare1.setMailConfigKbn(MailConfigKbn.SPARE1);
		
		if(configSpare1.isValiable()){
			configs.add(configSpare1);
		}
		
		//add mail config spare 2
		mailFromAddress = settings.get(ImmutableValues.APP_CONF_MAIL_FROMADDRESS_SPARE_2_KEY);
		mailSmtpHost = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPHOST_SPARE_2_KEY);
		mailSmtpPort = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPPORT_SPARE_2_KEY);
		mailSmtpAuth = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPAUTH_SPARE_2_KEY);
		mailEncrypt = settings.get(ImmutableValues.APP_CONF_MAIL_ENCRYPT_SPARE_2_KEY);
		mailUserName = settings.get(ImmutableValues.APP_CONF_MAIL_USERNAME_SPARE_2_KEY);
		mailUserPwd = settings.get(ImmutableValues.APP_CONF_MAIL_USERPWD_SPARE_2_KEY);
		mailTo = settings.get(ImmutableValues.APP_CONF_MAILTO_SPARE_2_KEY);
		toAddress = StringUtils.strToList(mailTo);
		
		MailConfig configSpare2 = new MailConfig();
		configSpare2.setSmtpHost(mailSmtpHost);
		configSpare2.setSmtpPort(mailSmtpPort);
		configSpare2.setSmtpAuth(mailSmtpAuth);
		configSpare2.setFromAddress(mailFromAddress);
		configSpare2.setUserName(mailUserName);
		configSpare2.setUserPassword(mailUserPwd);
		configSpare2.setToAddress(toAddress);
		configSpare2.setSmtpEncryptType(mailEncrypt);
		configSpare2.setMailConfigKbn(MailConfigKbn.SPARE2);
		
		if(configSpare2.isValiable()){
			configs.add(configSpare2);
		}
		
		return configs;
	}
	
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public List<String> getToAddress() {
		return toAddress;
	}
	public void setToAddress(List<String> toAddress) {
		this.toAddress = toAddress;
	}
	public List<String> getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(List<String> ccAddress) {
		this.ccAddress = ccAddress;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getSmtpAuth() {
		return smtpAuth;
	}
	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	/**
	 * @return the smtpEncryptType
	 */
	public String getSmtpEncryptType() {
		return smtpEncryptType;
	}

	/**
	 * @param smtpEncryptType the smtpEncryptType to set
	 */
	public void setSmtpEncryptType(String smtpEncryptType) {
		this.smtpEncryptType = smtpEncryptType;
	}
	
	/**
	 * @return the mailConfigKbn
	 */
	public MailConfigKbn getMailConfigKbn() {
		return mailConfigKbn;
	}

	/**
	 * @param mailConfigKbn the mailConfigKbn to set
	 */
	public void setMailConfigKbn(MailConfigKbn mailConfigKbn) {
		this.mailConfigKbn = mailConfigKbn;
	}

	public boolean isValiable(){
		boolean ret = true;
		if(TextUtils.isEmpty(smtpHost) || TextUtils.isEmpty(smtpPort)
				|| TextUtils.isEmpty(smtpAuth) || TextUtils.isEmpty(smtpEncryptType)
				|| TextUtils.isEmpty(fromAddress) || TextUtils.isEmpty(userName)
				|| TextUtils.isEmpty(userPassword) || toAddress == null || toAddress.size() == 0){
			ret = false;
		}
		return ret;
	}
	
}
