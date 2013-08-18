package com.aks.lendit.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import java.lang.StringBuffer;
import java.net.URLDecoder;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class StringUtil {
	private static final Logger logger = Logger.getLogger(StringUtil.class);

	public static boolean isNotNull(String s) {
		if(s == null || s.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static boolean isNull(String s) {
		return (!isNotNull(s));
	}

	public static String nullReplace(String mainStr, String changeStr) {
		return true == isNull(mainStr) ? changeStr : mainStr;
	}

	public static String nullReplace(Object mainStr, String changeStr) {
		return mainStr == null ? changeStr : mainStr.toString();
	}

	public static String caseReplace(String mainStr, String equalsStr, String changeStr) {
		if(true == isNotNull(mainStr)) {
			if(true == mainStr.equals(equalsStr)) {
				return changeStr;
			}
		} else if (true == isNull(equalsStr)) {
			return changeStr;
		}

		return mainStr;
	}

	/**
	 *
	 * key ������������ �����Ͽ� String �迭�� �����.
	 *
	 * @param reqMap
	 * @return
	 *
	 * @see TreeSet
	 */
	public static String[] sortKeys(Map  <String, String> reqMap) {
		Set  <String>keySet = new TreeSet <String> (reqMap.keySet());

		String key[] = new String[keySet.size()];

		Iterator it = keySet.iterator();
		int i = 0;
		while (it.hasNext()) {
			key[i++] = (String) it.next();
		}
		return key;
	}

	public static Map<String,String> stringToMapNotUsed(String str) {
		Map<String,String> map = new HashMap<String,String>();

		if(true == isNull(str)) {
			return map;
		}

		String[] colum = str.split("&");

		for(int i=0; i<colum.length; i++) {
			int idx = colum[i].indexOf("=");
			int len = colum[i].length();

			if(idx < 0) {
				logger.error("[FAIL] message string is incorrect : " + str);
				break;
			}

			String key = colum[i].substring(0, idx);
			String val = colum[i].substring(idx+1, len);

			map.put(key, val);
		}

		return map;
	}

     public static HashMap<String, String> getNameValuePairs(String strData, boolean bURLDecode) {
        HashMap<String, String> nameValuePairs = new HashMap<String, String>();
        if(isNull(strData)) {
            return nameValuePairs;
        }
        StringTokenizer stTokenizer = new StringTokenizer(strData, "&");
        while (stTokenizer.hasMoreTokens()) {
            String strNameValuePair = stTokenizer.nextToken();
            StringTokenizer stTokenizer2 = new StringTokenizer(strNameValuePair, "=");
            String strKey = null, strValue = null;
            try {
                strKey = stTokenizer2.nextToken();
                if (stTokenizer2.hasMoreTokens()) {
                    strValue = stTokenizer2.nextToken();
                }
                if (bURLDecode && strValue != null) {
                    strValue = URLDecoder.decode(strValue, "UTF-8");
                }
            } catch (Exception ex) {//ignore
                logger.error("exception while parsing an element in " + strData + ",key=" + strKey + ",value=" + strValue + ",EX=" + ex.getMessage());
            }
            if (isNotNull(strKey)) {
                nameValuePairs.put(strKey, StringUtil.nullReplace(strValue,""));
            }
        }
        return nameValuePairs;
    }
	

	public static String listToStringln(List list) {
		StringBuffer result = new StringBuffer("(");

		if(list == null || list.size() < 0) {
			return "()";
		}
		for(int i = 0; i < list.size(); i++) {
			Object o = list.get(i);

			if(o == null) {
				result.append(" [] ");
				continue;
			}

			result.append(" [").append(i).append(":").append(o.toString()).append("]\n");
		}

		return result.append(")").toString();
	}

	public static int countPattern(String str, String regex) throws Exception {
		try {
			int count = 0;

			int isPattern = 0;

			int strLen = str.length();

			while(isPattern <= strLen){
				isPattern = str.indexOf(regex, isPattern);

				if(isPattern == -1){
					break;
				}

				isPattern++;
				count++;
			}

			return count;

		} catch (Exception e) {
			throw new Exception("CountPatternException");
		}
	}

	public static boolean isNumber(String s) {
		if(s == null || s.length() == 0) {
			return false;
		}

		for(int i = 0; i < s.length(); i++) {
			int num = Character.digit(s.charAt(i), 10);

			if(num < 0) {
				return false;
			}
		}

       return true;
	}

    public static String getNonNull(String strValue) {
        return strValue == null?"":strValue;
    }

    public static String addParamsToURL(String url, String params) {
        if (url == null || url.trim().length() == 0) {
            url = "#";
        } else {
            url = url.trim();
            if (url.toLowerCase().startsWith("http") == false) {
                url = "http://" + url;
            }
        }
        if (url.indexOf("?") < 0) {
            url += "?";
        } else {
            url += "&";
        }
        url += params;
        return url;
    }
    
}
