package com.daipeng.phonemonitor.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daipeng.phonemonitor.comon.ImmutableValues;

import android.text.TextUtils;

public class StringUtils {
	public static List<String> strToList(String str,String seprator){
		List<String> ret = new ArrayList<String>();
		if(!TextUtils.isEmpty(str)){
			ret = Arrays.asList(TextUtils.split(str, seprator));
    	}
		return ret;
	}
	
	public static List<String> strToList(String str){
		return strToList(str, ImmutableValues.COMMA);
	}
}
