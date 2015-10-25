package com.daipeng.phonemonitor.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daipeng.phonemonitor.comon.ImmutableValues;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * SharedPreferences的读取和写入Utils
 * @author tang_penggui
 *
 */
public class PrefsUtils {
	
    private PrefsUtils() {
    }
     
    public static boolean save(Context context,Map<String, String> settings, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        for (String strKey : settings.keySet()) {
            editor.putString(strKey, settings.get(strKey));
        }
        return editor.commit();
    }
     
    @SuppressWarnings("unchecked")
	public static Map<String, String> read(Context context,String fileName) {
        Map<String, String> settings = new HashMap<String, String>();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        settings = (Map<String, String>) sp.getAll();
        return settings;
    }
    
}