package com.daipeng.phonemonitor.utils;

import android.util.Log;

/**
 * log utils
 * 自定义Log的处理方式。后期可能扩展为将计入数据库或文件
 * @author tang_penggui
 *
 */
public class LogUtils {
	
	public enum LogType{
		VERBOSE,
		DEBUG,
		INFO,
		WARN,
		ERROR,
		ASSERT
	}
	
	public static void v(String tag,String msg){
		if(isLogEnable(LogType.VERBOSE)){
			Log.v(tag, msg);
		}
	}
	
	public static void d(String tag,String msg){
		if(isLogEnable(LogType.DEBUG)){
			Log.d(tag, msg);
		}
	}
	
	public static void i(String tag,String msg){
		if(isLogEnable(LogType.INFO)){
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag,String msg){
		if(isLogEnable(LogType.INFO)){
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(isLogEnable(LogType.INFO)){
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag,String msg,Throwable throwable){
		if(isLogEnable(LogType.INFO)){
			Log.e(tag, msg, throwable);
		}
	}
	
	public static void log(LogType logType,String tag,String msg){
		switch (logType) {
		case VERBOSE:
			if(isLogEnable(LogType.VERBOSE)){
				Log.v(tag, msg);
			}
			break;
		case DEBUG:
			if(isLogEnable(LogType.DEBUG)){
				Log.d(tag, msg);
			}
			break;	
		case INFO:
			if(isLogEnable(LogType.INFO)){
				Log.i(tag, msg);
			}
			break;	
		case WARN:
			if(isLogEnable(LogType.WARN)){
				Log.w(tag, msg);
			}
			break;
		case ERROR:
			if(isLogEnable(LogType.ERROR)){
				Log.e(tag, msg);
			}
			break;
		default:
			break;
		}
	}

	private static boolean isLogEnable(LogType verbose) {
		// TODO tang_penggui read config file
		return true;
	}
	
	
}
