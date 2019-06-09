package com.forum.app.exception;

/**
 * This is the exception thrown when a database fetch (select) operation fails.
 * 
 * @author Saurabh Mhatre
 *
 */
public class FetchException extends ForumException {
	private static final long serialVersionUID = 1L;

	public FetchException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}
}
