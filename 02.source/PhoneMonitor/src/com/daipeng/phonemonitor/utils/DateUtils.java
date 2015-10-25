package com.daipeng.phonemonitor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils extends android.text.format.DateUtils{
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static String formateYMDHMS(Date date){
		return new SimpleDateFormat(YMDHMS,Locale.CHINA).format(new Date());
	}
}
