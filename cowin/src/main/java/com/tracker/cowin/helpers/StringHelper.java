package com.tracker.cowin.helpers;

public class StringHelper {
	public static boolean ifNullOrMatch(String str, Object match) {
		return match == null || match.toString().equalsIgnoreCase(str);
	}
}
