package com.daipeng.phonemonitor.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactUtils {
	public static String getContactNameFromPhoneBook(Context context, String phoneNum) {  
	    String contactName = "";  
	    ContentResolver cr = context.getContentResolver();  
	    Cursor pCur = cr.query(  
	            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,  
	            ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?",  
	            new String[] { phoneNum }, null);  
	    if (pCur.moveToFirst()) {  
	        contactName = pCur  
	                .getString(pCur  
	                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));  
	        pCur.close();  
	    }  
	    return contactName;  
	} 
}
