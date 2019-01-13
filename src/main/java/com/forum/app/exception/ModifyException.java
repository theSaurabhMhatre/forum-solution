package com.forum.app.exception;

public class ModifyException extends ForumException {
	private static final long serialVersionUID = 1L;
	
	public ModifyException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}
	
}
