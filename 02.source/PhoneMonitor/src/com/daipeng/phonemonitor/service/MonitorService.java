package com.daipeng.phonemonitor.service;

import com.daipeng.phonemonitor.broadcastreceiver.MonitorReceiver;
import com.daipeng.phonemonitor.comon.ImmutableValues;
import com.daipeng.phonemonitor.utils.LogUtils;
import com.daipeng.phonemonitor.utils.LogUtils.LogType;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Telephony.Sms.Intents;
import android.telephony.TelephonyManager;

/**
 * 由activity启动，负责注册和注销receiver
 * @author tang_penggui
 *
 */
public class MonitorService extends Service{
 
    private MonitorReceiver receiver;
    private IntentFilter receiverFilter;
     
    @Override
    public void onCreate()
    {
        //采用代码方式注册广播接收者
        receiver=new MonitorReceiver(this);
        receiverFilter=new IntentFilter();
        
        //android.intent.action.PHONE_STATE
        receiverFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        //android.provider.Telephony.SMS_RECEIVED
        receiverFilter.addAction(Intents.SMS_RECEIVED_ACTION);
        receiverFilter.addAction(Intent.ACTION_BATTERY_LOW);
//        receiverFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	LogUtils.log(LogType.DEBUG,ImmutableValues.MONITOR_SERVICE_TAG, "MonitorService onStartCommand start ...");
        registerReceiver(receiver, receiverFilter);
        LogUtils.log(LogType.DEBUG,ImmutableValues.MONITOR_SERVICE_TAG, "MonitorService onStartCommand end ...");
    	return START_STICKY;
    }
     
    @Override
    public void onDestroy()
    {
    	LogUtils.log(LogType.DEBUG,ImmutableValues.MONITOR_SERVICE_TAG, "MonitorService onDestroy start ...");
        unregisterReceiver(receiver);
        receiver=null;
        super.onDestroy();
        LogUtils.log(LogType.DEBUG,ImmutableValues.MONITOR_SERVICE_TAG, "MonitorService onDestroy end ...");
    }
    
    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }
}