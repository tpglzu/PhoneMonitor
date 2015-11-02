package com.daipeng.phonemonitor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils extends android.text.format.DateUtils{
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String YMDHMSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String YMD = "yyyy-MM-dd";
	
	public static String formateYMDHMS(Date date){
		return new SimpleDateFormat(YMDHMS,Locale.CHINA).format(new Date());
	}
	
	public static String formateYMDHMSS(Date date){
		return new SimpleDateFormat(YMDHMSS,Locale.CHINA).format(new Date());
	}
	
	public static String formateYMD(Date date){
		return new SimpleDateFormat(YMD,Locale.CHINA).format(new Date());
	}
}
