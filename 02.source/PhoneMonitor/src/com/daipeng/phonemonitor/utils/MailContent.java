package com.daipeng.phonemonitor.utils;

import java.io.File;
import java.util.List;

public class MailContent {
	private String subject;
	private String msgBody;
	private List<File> attachFile;
	private String sendResult;
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the msgBody
	 */
	public String getMsgBody() {
		return msgBody;
	}
	/**
	 * @param msgBody the msgBody to set
	 */
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	/**
	 * @return the attachFile
	 */
	public List<File> getAttachFile() {
		return attachFile;
	}
	/**
	 * @param attachFile the attachFile to set
	 */
	public void setAttachFile(List<File> attachFile) {
		this.attachFile = attachFile;
	}
	/**
	 * @return the sendResult
	 */
	public String getSendResult() {
		return sendResult;
	}
	/**
	 * @param sendResult the sendResult to set
	 */
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	
	
}
