package com.daipeng.phonemonitor.activity;

import com.daipeng.phonemonitor.R;
import com.daipeng.phonemonitor.comon.ImmutableValues;
import com.daipeng.phonemonitor.service.MonitorService;
import com.daipeng.phonemonitor.utils.LogUtils;
import com.daipeng.phonemonitor.utils.LogUtils.LogType;
import com.daipeng.phonemonitor.utils.ServiceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button startServiceButton;
	private Button stopServiceButton;
	private TextView serviceStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startServiceButton = (Button) findViewById(R.id.serviceStart);
		stopServiceButton = (Button) findViewById(R.id.serviceStop);
		serviceStatus = (TextView) findViewById(R.id.serviceStatus);		
		
        boolean isServiceRunning = ServiceUtils.isServiceRunning(this, MonitorService.class.getName());
        startServiceButton.setEnabled(!isServiceRunning);
        stopServiceButton.setEnabled(isServiceRunning);
        if(isServiceRunning){
        	serviceStatus.setText(R.string.activity_main_textview_servicestatus_running);
        }else{
        	serviceStatus.setText(R.string.activity_main_textview_servicestatus_stopping);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//do not display menu
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// do nothing
		return true;
	}
	
	/**
	 * �����趨
	 * @param source
	 */
	public void onSetting(View source){
		LogUtils.log(LogType.DEBUG,ImmutableValues.MAIN_ACTIVE_TAG, "onSetting start...");
		Intent intent = new Intent(this, SettingsActivity.class);
	    startActivity(intent);
		LogUtils.log(LogType.DEBUG,ImmutableValues.MAIN_ACTIVE_TAG, "onSetting end...");
	}
	
	/**
	 * Start Service
	 * @param source
	 */
	public void onStartService(View source){
		LogUtils.log(LogType.DEBUG,ImmutableValues.MAIN_ACTIVE_TAG, "onStartService start...");
		
		Intent intent=new Intent(this,MonitorService.class);
        startService(intent);

        String toastMsg;
        boolean isServiceRunning = ServiceUtils.isServiceRunning(this, MonitorService.class.getName());
        if(isServiceRunning){
        	toastMsg = "Service Start Successful";
        	startServiceButton.setEnabled(false);
        	stopServiceButton.setEnabled(true);
        	serviceStatus.setText(R.string.activity_main_textview_servicestatus_running);
        }else{
        	toastMsg = "Service Start Failed";
        }
        
        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
        
		LogUtils.log(LogType.DEBUG,ImmutableValues.MAIN_ACTIVE_TAG, "onStartService end...");
	}
	
	/**
	 * Stop Service
	 * @param source
	 */
	public void onStopService(View source){
		LogUtils.log(LogType.DEBUG,ImmutableValues.MAIN_ACTIVE_TAG, "onStopService start...");
		Intent intent=new Intent(this,MonitorService.class);
        stopService(intent);
        
        String toastMsg;
        boolean isServiceRunning = ServiceUtils.isServiceRunning(this, MonitorService.class.getName());
        if(!isServiceRunning){
        	toastMsg = "Service Stop Successful";
        	startServiceButton.setEnabled(true);
        	stopServiceButton.setEnabled(false);
        	serviceStatus.setText(R.string.activity_main_textview_servicestatus_stopping);
        }else{
        	toastMsg = "Service Stop Failed";
        }
        
        Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
        
		LogUtils.log(LogType.DEBUG,ImmutableValues.MAIN_ACTIVE_TAG, "onStopService end...");
	}
	
}