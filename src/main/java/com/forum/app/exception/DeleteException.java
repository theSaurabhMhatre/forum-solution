package com.forum.app.exception;

/**
 * This is the exception thrown when a database delete operation fails.
 * 
 * @author Saurabh Mhatre
 */
public class DeleteException extends ForumException {
	private static final long serialVersionUID = 1L;

	public DeleteException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}

}
