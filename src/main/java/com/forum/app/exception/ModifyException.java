package com.forum.app.exception;

/**
 * This is the exception thrown when a database save/update operation fails.
 * 
 * @author Saurabh Mhatre
 *
 */
public class ModifyException extends ForumException {
	private static final long serialVersionUID = 1L;

	public ModifyException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}

}
