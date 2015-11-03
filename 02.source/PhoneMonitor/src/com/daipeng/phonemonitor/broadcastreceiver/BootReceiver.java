package com.daipeng.phonemonitor.broadcastreceiver;

import com.daipeng.phonemonitor.comon.ImmutableValues;
import com.daipeng.phonemonitor.service.MonitorService;
import com.daipeng.phonemonitor.utils.LogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * When the phone boot, Start monitor service
 * 
 * @author tang_penggui
 *
 */
public class BootReceiver extends BroadcastReceiver implements ImmutableValues{
	@Override
	public void onReceive(Context context, Intent intent) {
		LogUtils.i(BOOT_RECEIVER_TAG, "Start monitor service after boot complete...");
		Intent service = new Intent(context, MonitorService.class);
		context.startService(service);
		
	}
}
