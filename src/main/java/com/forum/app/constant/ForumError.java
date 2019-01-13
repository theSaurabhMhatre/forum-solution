package com.forum.app.constant;

public enum ForumError {
	FETCH_ERROR("Fetch Unsuccessful"),
	MODIFY_ERROR("Modify Unsuccessful"),
	DELETE_ERROR("Delete Unsuccessful");
	
	private String message;
	
	private ForumError(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
