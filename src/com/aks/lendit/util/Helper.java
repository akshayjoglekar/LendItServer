package com.aks.lendit.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;


public class Helper {
	private static Logger logger = Logger.getLogger(Helper.class);
	
	public static boolean hasValue(String data) {
		if (data != null && !data.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String data) {
		if (data == null || data.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	private static final String emptyString = "";

	public static String getNonNull(String data) {
		if (data != null) {
			return data;
		}
		return emptyString;
	}

	public static String getNonNull(String data, String alternateVal) {
		if (data != null) {
			return data;
		}
		return alternateVal;
	}

	public static double roundToTwoDecimals(double dblValue) {
		return Math.round(dblValue * 100)/100.0d;
	}
	
	public static String getNameValueString(Map<String, String> resMap) {
		SortedSet<String> sortedset= new TreeSet<String>(resMap.keySet());
		Iterator<String> it = sortedset.iterator();
		StringBuilder stBuilder = new StringBuilder();
		try {
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = resMap.get(key);
				if(StringUtil.isNull(value))
					value = "";
				stBuilder.append(key).append("=").append(URLEncoder.encode(value,"UTF-8")).append("&");
			}
			if((stBuilder.length() > 0) && stBuilder.charAt(stBuilder.length() - 1) == '&') {
				stBuilder.deleteCharAt(stBuilder.length() - 1);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("[FAIL] Error while building response string - ", e);
		}
		return stBuilder.toString();
	}
}