package com.daipeng.phonemonitor.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.daipeng.phonemonitor.comon.ImmutableValues;

public class StringUtils {
	
	/**
	 * @param str
	 * @param seprator
	 * @return
	 */
	public static List<String> strToList(String str,String seprator){
		List<String> ret = new ArrayList<String>();
		if(isNotEmpty(str)){
			ret = Arrays.asList(split(str, seprator));
    	}
		return ret;
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static List<String> strToList(String str){
		return strToList(str, ImmutableValues.COMMA);
	}
	
    /**
     * @param str
     * @return
     */
    public static Long toLong(String str) {
        if (isEmpty(str)) {
            return null;
        }
        Long ret;
        try {
            ret = Long.parseLong(str);
        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }
    
    /**
     * @param str
     * @return
     */
    public static Integer toInt(String str) {
        if (isEmpty(str)) {
            return null;
        }
        Integer ret;
        try {
            ret = Integer.parseInt(str);
        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }
    
    /**
     * @param str
     * @return
     */
    public static String toLikeString(String str){
    	return toLikeString(str, true, true);
    }
    
    /**
     * @param str
     * @param front
     * @param back
     * @return
     */
    public static String toLikeString(String str, boolean front, boolean back) {
        if (str == null) {
            return "";
        }
        if (front) {
            str = '%' + str;
        }
        if (back) {
            str = str + '%';
        }
        return str;
    }
    
    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
    
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }
    
    /**
     * String.split() returns [''] when the string to be split is empty. This returns []. This does
     * not remove any empty strings from the result. For example split("a,", ","  ) returns {"a", ""}.
     *
     * @param text the string to split
     * @param expression the regular expression to match
     * @return an array of strings. The array will be empty if text is empty
     *
     * @throws NullPointerException if expression or text is null
     */
    public static String[] split(String text, String expression) {
        if (text.length() == 0) {
            return new String[]{};
        } else {
            return text.split(expression, -1);
        }
    }
}
