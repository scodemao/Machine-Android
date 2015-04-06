package com.sunhydraulics.machine.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author maoweiwei
 * 
 */
public class StringUtils {
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			byte messageDigest[] = digest.digest(s.getBytes("UTF-8"));

			BigInteger bigInt = new BigInteger(1, messageDigest);
			String hexString = bigInt.toString(16);
			while (hexString.length() < 32) {
				hexString = "0" + hexString;
			}
			return hexString;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isReallyEmpty(String str) {
		if (str == null || str.trim().length() == 0
				|| str.equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static int unicodeLength(String str) {
		str = str.replaceAll("[^\\x00-\\xff]", "**");
		int length = str.length();
		return length / 2;
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}
}
