package com.daipeng.phonemonitor.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactUtils {
	
	/**
	 * Get Contact Name From Phone Book
	 * @param context
	 * @param phoneNum
	 * @return
	 */
	public static String getContactNameFromPhoneBook(Context context, String phoneNum) {  
	    String contactName = "";  
	    ContentResolver cr = context.getContentResolver();  
	    Cursor pCur = cr.query(  
	            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,  
	            ContactsContract.CommonDataKinds.Phone.NUMBER + " like ?",  
	            new String[] { StringUtils.toLikeString(phoneNum) }, null);  
	    if (pCur.moveToFirst()) {  
	        contactName = pCur  
	                .getString(pCur  
	                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));  
	        pCur.close();  
	    }  
	    return contactName;  
	} 
}
