package com.daipeng.phonemonitor.utils;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.daipeng.phonemonitor.comon.ImmutableValues;

/**
 * mail send utils
 * @author tang_penggui
 *
 */
public class MailSenderUtils {
	
    private MailSenderUtils(){
        
    }
    
    
    /**
     * send the mail. if mail config can not send mail,use the spare try to send
     * @param mailConfigs
     * @param mailContent
     * @return
     */
    public static boolean send(List<MailConfig> mailConfigs,MailContent mailContent){
    	if(mailConfigs == null || mailConfigs.size() < 1){
    		LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "★★★  do not have useabled mail configuration ... ★★★ ");
    		return false;
    	}
    	
    	for (MailConfig mailConfig : mailConfigs) {
			boolean sendResult = send(mailConfig, mailContent);
			LogUtils.i(ImmutableValues.MAIL_SEND_TAG, "Send mail use config : " + mailConfig.getMailConfigKbn().getName() + ", result : " + sendResult);
			if(sendResult){
				return true;
			}
		}
    	
    	return false;
    }
     
    /**
     * send the mail
     * @param mailConfig mail config
     * @param mailContent mail content
     * @return true:send successful,false:send failed
     */
    public static boolean send(final MailConfig mailConfig,MailContent mailContent){
    	
    	if(mailConfig == null){
    		LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "★★★ mail config not defined ★★★ ");
    	}
    	
    	Properties properties = getMailProperties(mailConfig);

    	if(properties == null){
    		LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "★★★ mail config not correct ...★★★ ");
    	}
    	
        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
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
//            	LogUtils.d(ImmutableValues.MAIL_SEND_TAG,"send to : " + toAddr);
            	message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
			}
            
            message.setSubject(mailContent.getSubject());
            message.setText(mailContent.getMsgBody());

            List<File> attachFiles = mailContent.getAttachFile();
            if(attachFiles != null && !attachFiles.isEmpty()){
            	Multipart mp = new MimeMultipart("mixed");  
                MimeBodyPart mbp = new MimeBodyPart();    
                mbp.setText(mailContent.getMsgBody());
                mp.addBodyPart(mbp);
	            for (File efile : attachFiles) {
	            	mbp=new MimeBodyPart();  
	                FileDataSource fds=new FileDataSource(efile); //得到数据源  
	                mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart  
	                mbp.setFileName(fds.getName());  //得到文件名同样至入BodyPart  
	                mp.addBodyPart(mbp);  
				}
                message.setContent(mp); //Multipart加入到信件 
            }
            message.saveChanges();  
            
            sendMessage(message, session, mailConfig);
        } catch (Exception e) {
        	LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "mail send error...",e);
        	e.printStackTrace();
        	mailContent.setSendResult("mail send error...Exception:" + e.getCause());
        	return false;
        }
        mailContent.setSendResult("mail send successful..");
        return true;
    }
    
    
    
    public static String assembleMailBodyForPhone(String telNo,String contactName,String time, long ringTimesSec){
    	StringBuilder sb = new StringBuilder();
    	sb.append("★★★★★新来电提醒★★★★★\n");
    	sb.append("【来电时间】："+ time +"\n");
    	sb.append("【来电号码】："+ telNo +"\n");
    	sb.append("【来电人名】："+ contactName +"\n");
    	sb.append("【振铃时间】："+ ringTimesSec +"秒 \n");//FIXME tang_penggui use strings resource
    	
    	return sb.toString();
    }

	public static String assembleMailBodyForSMS(String smsMsgDate, String contactName,String smsMsgNumber, String smsMsgBody) {
		StringBuilder sb = new StringBuilder();
    	sb.append("★★★★★新短信提醒★★★★★\n");
    	sb.append("【短信时间】："+ smsMsgDate +"\n");
    	sb.append("【短信号码】："+ smsMsgNumber +"\n");
    	sb.append("【短信人名】："+ contactName +"\n");
    	sb.append("【短信内容】："+ smsMsgBody +"\n");//FIXME tang_penggui use strings resource
    	
    	return sb.toString();
	} 
	
	public static String assembleMailBodyForBattery(String nowDate,String batteryPercent){
		StringBuilder sb = new StringBuilder();
    	sb.append("★★★★★电池电量提醒★★★★★\n");
    	sb.append("【提醒时间】："+ nowDate +"\n");
    	sb.append("【剩余电量】："+ batteryPercent +"\n");//FIXME tang_penggui use strings resource
    	
    	return sb.toString();
	}
	
	private static Properties getMailProperties(MailConfig mailConfig){
		
		Properties properties =new Properties();
		
		String mailEncryptType = mailConfig.getSmtpEncryptType();
		properties.put(MailConfig.KEY_MAIL_SMTP_HOST, mailConfig.getSmtpHost());
		properties.put(MailConfig.KEY_MAIL_SMTP_AUTH, mailConfig.getSmtpAuth());
		properties.put(MailConfig.KEY_MAIL_SMTP_PORT, mailConfig.getSmtpPort());
		properties.put(MailConfig.KEY_MAIL_SMTP_DEBUG, "true");
		
		if(ImmutableValues.APP_CONF_MAIL_ENCRYPT_TLS.equals(mailEncryptType)){
			//TLS
	        properties.put(MailConfig.KEY_MAIL_SMTP_STARTTTLS_ENABLE, "true");
		}else if(ImmutableValues.APP_CONF_MAIL_ENCRYPT_SSL.equals(mailEncryptType)){
			//SSL
			properties.put(MailConfig.KEY_MAIL_SMTP_SSL_FACTORYPORT, mailConfig.getSmtpPort());
			properties.put(MailConfig.KEY_MAIL_SMTP_SSL_FACTORYCLASS,MailConfig.VALUE_MAIL_SMTP_SSL_FACTORYCLASS);
			properties.put(MailConfig.KEY_MAIL_SMTP_SSL_FALLBACK, MailConfig.VALUE_MAIL_SMTP_SSL_FALLBACK);
		}else{
			//Not defined
			LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "unknown encrypt type. mail not sended ...");
			properties = null;
		}
		
		return properties;
        
	}
	
	private static void sendMessage(MimeMessage message,Session session,MailConfig mailConfig) throws Exception{
		String mailEncryptType = mailConfig.getSmtpEncryptType();
		if(ImmutableValues.APP_CONF_MAIL_ENCRYPT_SSL.equals(mailEncryptType)){
			//SSL
			Transport transport = null;
			try{
			transport = session.getTransport("smtp");
            
            transport.connect(mailConfig.getSmtpHost(),
            		StringUtils.toInt(mailConfig.getSmtpPort()),
            		mailConfig.getUserName(),
                    mailConfig.getUserPassword());
             
            transport.sendMessage(message, message.getAllRecipients());
			}catch(Exception e){
				throw e;
			}finally{
				if (transport != null) {
					try {
						transport.close();
					} catch (Exception ex) {
					}
				}
			}
		}else if(ImmutableValues.APP_CONF_MAIL_ENCRYPT_TLS.equals(mailEncryptType)){
			//TLS
			Transport.send(message);
		}else{
			//Do nothing
			LogUtils.e(ImmutableValues.MAIL_SEND_TAG, "unknown encrypt type. mail not sended ...");
		}
	}
	
}
