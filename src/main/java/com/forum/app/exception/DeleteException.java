package com.forum.app.exception;

public class DeleteException extends ForumException {
	private static final long serialVersionUID = 1L;
	
	public DeleteException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}
	
}
