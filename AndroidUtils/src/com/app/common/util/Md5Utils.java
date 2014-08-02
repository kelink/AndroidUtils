package com.app.common.util;

import java.security.MessageDigest;

public class Md5Utils {
	private static final String HEX_DIGITS[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (byte element : b) {
			resultSb.append(Md5Utils.byteToHexString(element));
		}

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return Md5Utils.HEX_DIGITS[d1] + Md5Utils.HEX_DIGITS[d2];
	}

	public static String md5Encode(String origin) {
		return Md5Utils.md5Encode(origin, "utf-8");
	}

	public static String md5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (StringUtils.isBlank(charsetname)) {
				resultString = Md5Utils.byteArrayToHexString(md
						.digest(resultString.getBytes()));
			} else {
				resultString = Md5Utils.byteArrayToHexString(md
						.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception e) {

		}
		return resultString;
	}

}
