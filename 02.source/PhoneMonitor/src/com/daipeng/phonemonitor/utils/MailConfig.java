package com.daipeng.phonemonitor.utils;

import java.io.File;
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
	public static final String KEY_MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String KEY_MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String KEY_MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String KEY_MAIL_SMTP_STARTTTLS_ENABLE = "mail.smtp.starttls.enable";
	
	private String fromAddress;
	private String userName;
	private String userPassword;
	private List<String> toAddress;
	private List<String> ccAddress;
	private String subject;
	private String msgBody;
	
	private String smtpHost;
	private String smtpPort;
	private String smtpAuth;
	private String smtpStarttlsEnabled;
	
	private String sendResult;
	
	private List<File> attachFile;
	
	public List<File> getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(List<File> attachFile) {
		this.attachFile = attachFile;
	}

	public MailConfig(){}
	
	public static MailConfig loadMailConfig(Context context){
		Map<String,String> settings = PrefsUtils.read(context, ImmutableValues.APP_CONF_FILE_NAME);
		
		return loadMailConfig(settings);
	}
	
	public static MailConfig loadMailConfig(Map<String, String> settings) {
		MailConfig config = new MailConfig();
		
		String mailFromAddress = settings.get(ImmutableValues.APP_CONF_MAIL_FROMADDRESS_KEY);
		String mailSmtpHost = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPHOST_KEY);
		String mailSmtpPort = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPPORT_KEY);
		String mailSmtpAuth = settings.get(ImmutableValues.APP_CONF_MAIL_SMTPAUTH_KEY);
		String mailStartTls = settings.get(ImmutableValues.APP_CONF_MAIL_STARTTLS_KEY);
		String mailUserName = settings.get(ImmutableValues.APP_CONF_MAIL_USERNAME_KEY);
		String mailUserPwd = settings.get(ImmutableValues.APP_CONF_MAIL_USERPWD_KEY);
		String mailTo = settings.get(ImmutableValues.APP_CONF_MAILTO_KEY);
		List<String> toAddress = StringUtils.strToList(mailTo);
		
		config.setSmtpHost(mailSmtpHost);
		config.setSmtpPort(mailSmtpPort);
		config.setSmtpStarttlsEnabled(mailStartTls);
		config.setSmtpAuth(mailSmtpAuth);
		config.setFromAddress(mailFromAddress);
		config.setUserName(mailUserName);
		config.setUserPassword(mailUserPwd);
		config.setToAddress(toAddress);
		return config;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
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
	public String getSmtpStarttlsEnabled() {
		return smtpStarttlsEnabled;
	}
	public void setSmtpStarttlsEnabled(String smtpStarttlsEnabled) {
		this.smtpStarttlsEnabled = smtpStarttlsEnabled;
	}

	public String getSendResult() {
		return sendResult;
	}

	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	
	public boolean isValiable(){
		boolean ret = true;
		if(TextUtils.isEmpty(smtpHost) || TextUtils.isEmpty(smtpPort)
				|| TextUtils.isEmpty(smtpAuth) || TextUtils.isEmpty(smtpStarttlsEnabled)
				|| TextUtils.isEmpty(fromAddress) || TextUtils.isEmpty(userName)
				|| TextUtils.isEmpty(userPassword)){
			ret = false;
		}
		return ret;
	}

	
}
