package com.forum.app.util;

import java.util.Base64;

public class StringUtility {

	public static String encodeString(String plainString) {
		String encodedString = Base64.getEncoder().encodeToString(
				plainString.getBytes());
		return encodedString;
	}

	public static String decodeString(String encodedString) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}
	
}
