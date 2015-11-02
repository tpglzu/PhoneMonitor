package com.daipeng.phonemonitor.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Environment;
import android.util.Log;

/**
 * log utils
 * 自定义Log的处理方式。后期可能扩展为将计入数据库或文件
 * @author tang_penggui
 *
 */
public class LogUtils {
	
	public static final String LOG_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "PhoneMonitor";  
	public static final String LOG_FILE_NAME = "PhoneMonitor";
	
	public enum LogType{
		VERBOSE("VERBOSE"),
		DEBUG("DEBUG"),
		INFO("INFO"),
		WARN("WARN"),
		ERROR("ERROR"),
		ASSERT("ASSERT");
		
		String level;
		LogType(String level){
			this.level = level;
		}
	}
	
	public static void v(String tag,String msg){
		if(isLogEnable(LogType.VERBOSE)){
			Log.v(tag, msg);
			doLog(LogType.VERBOSE,tag,msg);
		}
	}
	
	public static void d(String tag,String msg){
		if(isLogEnable(LogType.DEBUG)){
			Log.d(tag, msg);
			doLog(LogType.DEBUG,tag,msg);
		}
	}
	
	public static void i(String tag,String msg){
		if(isLogEnable(LogType.INFO)){
			Log.i(tag, msg);
			doLog(LogType.INFO,tag,msg);
		}
	}
	
	public static void w(String tag,String msg){
		if(isLogEnable(LogType.WARN)){
			Log.w(tag, msg);
			doLog(LogType.WARN,tag,msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(isLogEnable(LogType.ERROR)){
			Log.e(tag, msg);
			doLog(LogType.ERROR,tag,msg);
		}
	}
	
	public static void e(String tag,String msg,Throwable throwable){
		if(isLogEnable(LogType.INFO)){
			Log.e(tag, msg, throwable);
			doLog(LogType.INFO,tag,msg + "\n" + Log.getStackTraceString(throwable));
		}
	}
	
	public static void log(LogType logType,String tag,String msg){
		switch (logType) {
		case VERBOSE:
			if(isLogEnable(LogType.VERBOSE)){
				v(tag, msg);
			}
			break;
		case DEBUG:
			if(isLogEnable(LogType.DEBUG)){
				d(tag, msg);
			}
			break;	
		case INFO:
			if(isLogEnable(LogType.INFO)){
				i(tag, msg);
			}
			break;	
		case WARN:
			if(isLogEnable(LogType.WARN)){
				w(tag, msg);
			}
			break;
		case ERROR:
			if(isLogEnable(LogType.ERROR)){
				e(tag, msg);
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
	
	private static String doLog(LogType logType,String tag,String message) {

		File dir = new File(LOG_FILE_DIR);
		String fileName = LOG_FILE_NAME+"_"+DateUtils.formateYMD(new Date())+".log";
		
		if (!dir.exists()){
			boolean isOk = dir.mkdirs();
			Log.i("LogUtils", "create dir [" + dir.getAbsolutePath()	 +  "] ret : " + isOk);
		}
			
		File logFile = new File(dir,fileName);
		if(!logFile.exists()){
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				Log.e("LogUtils", "Create New Log File Error ... ",e);
			}
		}
		
		String time = DateUtils.formateYMDHMSS(new Date());

		String logMsg = time+"\t" + logType.level + "\t" + tag + "\t" + message + "\n";
		// 保存文件
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				
				FileOutputStream fos = new FileOutputStream(new File(dir,
						 fileName),true);
				fos.write(logMsg.getBytes());
				fos.close();
				return fileName;
			} catch (Exception e) {
				Log.e("LogUtils", "Log Write Error ... ",e);
			}
		}
		return null;
	}
	
	public static String getLogLocation(){
		return LOG_FILE_DIR;
	}
	
	public static List<File> getLogFiles(int max){
		List<File> result = new ArrayList<File>();
		File dir = new File(LOG_FILE_DIR);
		if(dir.exists()){
			File [] files = dir.listFiles();  
			if(files.length > 0){
				Arrays.sort(files, new Comparator<File>(){
					public int compare(File f1, File f2) {
						long diff = f1.lastModified() - f2.lastModified();
						if (diff > 0)
							return 1;
						else if (diff == 0)
							return 0;
						else
							return -1;
					}
				});
				int cnt = 0;
				for (File file : files) {
					if(cnt < max){
						result.add(file);
						cnt++;
					}else{
						break;
					}	
				}
			}
			
		}
		return result;
	}
}
