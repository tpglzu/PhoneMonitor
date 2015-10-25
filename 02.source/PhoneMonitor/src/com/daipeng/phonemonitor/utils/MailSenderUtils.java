package com.daipeng.phonemonitor.utils;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.daipeng.phonemonitor.comon.ImmutableValues;

/**
 * mail send utils
 * @author tang_penggui
 *
 */
public class MailSenderUtils {
	
    private MailSenderUtils(){
        
    }
     
    public static void send(final MailConfig mailConfig){
    	
    	if(mailConfig == null){
    		LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "���� mail config not defined ���� ");
    	}
    	
    	Properties properties =System.getProperties();
        properties.put(MailConfig.KEY_MAIL_SMTP_HOST, mailConfig.getSmtpHost());
        properties.put(MailConfig.KEY_MAIL_SMTP_PORT, mailConfig.getSmtpPort());
        properties.put(MailConfig.KEY_MAIL_SMTP_AUTH, mailConfig.getSmtpAuth());
        properties.put(MailConfig.KEY_MAIL_SMTP_STARTTTLS_ENABLE, mailConfig.getSmtpStarttlsEnabled());

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(mailConfig.getUserName(),mailConfig.getUserPassword());
            }
        });
        MimeMessage message = new MimeMessage(session);
         
        try {
            String from = mailConfig.getFromAddress();
            List<String> to = mailConfig.getToAddress();
            message.setFrom(new InternetAddress(from));
            
            for (String toAddr : to) {
            	LogUtils.d(ImmutableValues.MAIL_SEND_TAG,"send to : " + toAddr);
            	message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
			}

            message.setSubject(mailConfig.getSubject());
            message.setText(mailConfig.getMsgBody());
            Transport.send(message);
        } catch (Exception e) {
        	LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "mail send error...",e);
        	mailConfig.setSendResult("mail send error...Exception:" + e.getCause());
        	return;
        }
        mailConfig.setSendResult("mail send successful..");
    }
    
    public static String assembleMailBodyForPhone(String telNo,String contactName,String time, long ringTimesSec){
    	StringBuilder sb = new StringBuilder();
    	sb.append("���������������ѡ�����\n");
    	sb.append("������ʱ�䡿��"+ time +"\n");
    	sb.append("��������롿��"+ telNo +"\n");
    	sb.append("��������������"+ contactName +"\n");
    	sb.append("������ʱ�䡿��"+ ringTimesSec +"�� \n");//FIXME tang_penggui use strings resource
    	
    	return sb.toString();
    }

	public static String assembleMailBodyForSMS(String smsMsgDate, String contactName,String smsMsgNumber, String smsMsgBody) {
		StringBuilder sb = new StringBuilder();
    	sb.append("�������¶������ѡ�����\n");
    	sb.append("������ʱ�䡿��"+ smsMsgDate +"\n");
    	sb.append("�����ź��롿��"+ smsMsgNumber +"\n");
    	sb.append("��������������"+ contactName +"\n");
    	sb.append("���������ݡ���"+ smsMsgBody +"\n");//FIXME tang_penggui use strings resource
    	
    	return sb.toString();
	} 
	
	public static String assembleMailBodyForBattery(String nowDate,String batteryPercent){
		StringBuilder sb = new StringBuilder();
    	sb.append("�������ص������ѡ�����\n");
    	sb.append("������ʱ�䡿��"+ nowDate +"\n");
    	sb.append("��ʣ���������"+ batteryPercent +"\n");//FIXME tang_penggui use strings resource
    	
    	return sb.toString();
	}
	
}
