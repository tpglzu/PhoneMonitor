package com.daipeng.phonemonitor.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {
	/** 
	 * ����������״̬�����ж� 
	 * @return  true, ���ã� false�� ������ 
	 */  
	public static boolean isOpenNetwork(Context context) {  
	    ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
	    if(connManager.getActiveNetworkInfo() != null) {  
	        return connManager.getActiveNetworkInfo().isAvailable();  
	    }  
	    return false;  
	}  
}
