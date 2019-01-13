package com.forum.app.exception;

public class ForumException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public ForumException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
