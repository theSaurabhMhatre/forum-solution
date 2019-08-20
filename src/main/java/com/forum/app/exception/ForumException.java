package com.forum.app.exception;

/**
 * This is the parent exception class for the application, all other exceptions
 * extend it.
 * 
 * @author Saurabh Mhatre
 */
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
