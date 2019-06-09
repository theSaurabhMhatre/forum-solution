package com.forum.app.constant;

/**
 * This provides descriptions for validation related operations.
 * 
 * @author Saurabh Mhatre
 *
 */
public enum ForumValidation {
	VALIDATION_SUCCESS("Validation Successful"),

	VALIDATION_FAILURE("Validation Unsuccessful");

	private String message;

	private ForumValidation(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
