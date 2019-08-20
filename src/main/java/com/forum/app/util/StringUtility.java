package com.forum.app.util;

import java.util.Base64;

/**
 * Utility class for performing various operations on strings.
 * 
 * @author Saurabh Mhatre
 */
public class StringUtility {

	/**
	 * Encodes a given plain text into base64 string.
	 * 
	 * @param plainString the user readable string.
	 *
	 * @return A String instance of the encoded plainString.
	 */
	public static String encodeString(String plainString) {
		String encodedString = Base64.getEncoder().encodeToString(plainString.getBytes());
		return encodedString;
	}

	/**
	 * Decodes a given base64 string into plain text.
	 * 
	 * @param encodedString the encoded string.
	 *
	 * @return A String instance of the decoded encodedString.
	 */
	public static String decodeString(String encodedString) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}

}
