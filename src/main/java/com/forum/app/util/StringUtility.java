package com.forum.app.util;

import java.util.Base64;

/**
 * Utility class for performing various operations on strings.
 * 
 * @author Saurabh Mhatre
 *
 */
public class StringUtility {

	/**
	 * Encodes a given plain text into base64 string.
	 * 
	 * @param String plainString
	 * @return String encodedString
	 */
	public static String encodeString(String plainString) {
		String encodedString = Base64.getEncoder().encodeToString(plainString.getBytes());
		return encodedString;
	}

	/**
	 * Decodes a given base64 string into plain text.
	 * 
	 * @param String encodedString
	 * @return String decodedString
	 */
	public static String decodeString(String encodedString) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}

}
