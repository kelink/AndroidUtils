package com.app.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtils {
	public static String getSHA(String key) {
		MessageDigest messageDigest = null;
		String shaStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA");
			messageDigest.update(key.getBytes());
			shaStr = byte2hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return shaStr;

	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
}
