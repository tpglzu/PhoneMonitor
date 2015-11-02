package com.daipeng.phonemonitor.tasks;

import com.daipeng.phonemonitor.comon.ImmutableValues;
import com.daipeng.phonemonitor.utils.LogUtils;
import com.daipeng.phonemonitor.utils.MailConfig;
import com.daipeng.phonemonitor.utils.MailSenderUtils;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.text.TextUtils;

public class AsyncMailSendTask extends AsyncTask<Uri.Builder, Void, String> {
	
	private MailConfig mailConfig;
	
	public AsyncMailSendTask(MailConfig mailConfig){
		this.mailConfig = mailConfig;
	}
	
	@Override
	protected String doInBackground(Builder... params) {
    	try {
    		// do send mail
    		LogUtils.d(ImmutableValues.MAIL_SEND_TASK_TAG, "task start successful...");
    		MailSenderUtils.send(mailConfig);
        } catch (Exception e) {
            return e.toString();
        }
    	return null;
     }

    @Override
    protected void onPostExecute(String result) {
    	if(TextUtils.isEmpty(result)){
    		LogUtils.i(ImmutableValues.MAIL_SEND_TASK_TAG, "mail send successesful...");
    	}else{
    		LogUtils.e(ImmutableValues.MAIL_SEND_TASK_TAG, "mail send failed..."+result);
    	}
    }
}