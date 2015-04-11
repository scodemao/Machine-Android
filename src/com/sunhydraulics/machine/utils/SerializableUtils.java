package com.sunhydraulics.machine.utils;

import java.io.Serializable;
import java.util.Date;

public class SerializableUtils {
	public static Long toLong(Serializable s) {
		return null == s ? null : (Long) s;
	}

	public static Integer toInteger(Serializable s) {
		return null == s ? null : (Integer) s;
	}

	public static Date toDate(Serializable s) {
		return null == s ? null : (Date) s;
	}

	public static Double toDouble(Serializable s) {
		return null == s ? null : (Double) s;
	}

	public static Boolean toBoolean(Serializable s) {
		return null == s ? null : (Boolean) s;
	}

	public static Float toFloat(Serializable s) {
		return null == s ? null : (Float) s;
	}
}
