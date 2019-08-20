package com.forum.app.common;

import javax.ws.rs.core.Response.Status;

/**
 * This serves as the wrapper for all the responses returned by the application.
 * 
 * @author Saurabh Mhatre
 */
public class ResponseEntity {
	private Status responseStatus;

	private String responseMessage;

	private Object responseObject;

	public String getResponseMessage() {
		return responseMessage;
	}

	public Status getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Status ok) {
		this.responseStatus = ok;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

}
