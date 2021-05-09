package com.tracker.cowin.helpers;

public class StringHelper {
	private StringHelper() {}
	public static boolean ifNullOrMatch(String str, Object match) {
		return match == null || match.toString().equalsIgnoreCase(str);
	}
	
	public static String convertIntegerToString(Integer value) {
		return (value != null)?value.toString():null;
	}
	
	public static Integer convertStringInteger(String value) {
		return (value != null)?Integer.valueOf(value):null;
	}

}
