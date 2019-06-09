package com.forum.app.constant;

/**
 * This provides error descriptions for exceptions which occur during requested
 * operations.
 * 
 * @author Saurabh Mhatre
 *
 */
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
