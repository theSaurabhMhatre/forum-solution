package com.forum.app.exception;

public class FetchException extends ForumException {
	private static final long serialVersionUID = 1L;
	
	public FetchException(String message) {
		super(message);
	}

	public String getMessage() {
		return super.getMessage();
	}	
}
