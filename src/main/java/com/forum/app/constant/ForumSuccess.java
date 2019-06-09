package com.forum.app.constant;

/**
 * This provides success descriptions for requests which are performed
 * successfully.
 * 
 * @author Saurabh Mhatre
 *
 */
public enum ForumSuccess {
	FETCH_SUCCESS("Fetch Successful"),

	MODIFY_SUCCESS("Modify Successful"),

	DELETE_SUCCESS("Delete Successful");

	private String message;

	private ForumSuccess(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
