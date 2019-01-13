package com.forum.app.constant;

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
