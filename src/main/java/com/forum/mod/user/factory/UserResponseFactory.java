package com.forum.mod.user.factory;

import java.util.List;

import javax.ws.rs.core.Response;

import com.forum.app.common.ResponseEntity;
import com.forum.app.constant.ForumSuccess;
import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.mod.user.service.UserEntity;

public class UserResponseFactory {
	private UserBusinessFactory businessFactory;

	public UserResponseFactory(UserBusinessFactory businessFactory) {
		this.businessFactory = businessFactory;
	}

	public ResponseEntity getUser(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			UserEntity userEntity = businessFactory.getUser(userId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(userEntity);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity addUser(UserEntity userEntity) {
		ResponseEntity response = new ResponseEntity();
		try {
			userEntity = businessFactory.addUser(userEntity);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS
					.getMessage());
			response.setResponseObject(userEntity);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity updateUser(Long userId, UserEntity userEntity) {
		ResponseEntity response = new ResponseEntity();
		try {
			userEntity = businessFactory.updateUser(userId, userEntity);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS
					.getMessage());
			response.setResponseObject(userEntity);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity deleteUser(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			businessFactory.deleteUser(userId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.DELETE_SUCCESS
					.getMessage());
			response.setResponseObject(ForumSuccess.DELETE_SUCCESS.getMessage());
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity validateUser(String userName, UserEntity userEntity) {
		ResponseEntity response = new ResponseEntity();
		try {
			userEntity = businessFactory.validateUser(userName, userEntity);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumValidation.VALIDATION_SUCCESS
					.getMessage());
			response.setResponseObject(userEntity);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}
	
	public ResponseEntity getUserRankings() {
		ResponseEntity response = new ResponseEntity();
		try {
			List<List<Object>> userRankings = businessFactory.getUserRankings();
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(userRankings);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}
}