package com.forum.mod.user.factory;

import org.apache.commons.lang3.StringUtils;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.app.util.ValidationUtility;
import com.forum.mod.user.service.UserEntity;

public class UserValidationFactory {
	
	public static void validateUserId(Long userId) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(userId);
		if(check) {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public static void validateUserName(String userName) throws ForumException {
		Boolean check = false;
		check = StringUtils.isEmpty(userName);
		if(check) {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public static void validateAddUser(UserEntity userEntity) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(userEntity);
		check = check == true ? check : ValidationUtility.isNotNull(userEntity.getUserId());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserName());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserPswd());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserMail());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserBio());
		if(check) {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public static void validateUpdateUser(UserEntity userEntity, Long userId, String attribute) 
			throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(userEntity);
		check = check == true ? check : ValidationUtility.isNull(userId);
		check = check == true ? check : ValidationUtility.isNull(userId);
		check = check == true ? check : ValidationUtility.isNull(attribute);
		check = check == true ? check : ValidationUtility.isNull(userEntity.getUserId());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserName());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserPswd());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserMail());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserBio());
		check = check == true ? check : userId.equals(userEntity.getUserId()) == false;
		check = check == true ? check : ValidationUtility.validateAttribute(attribute);
		if(check) {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}	
	}
	
	public static void validateValidateUser(String userName, UserEntity userEntity) 
			throws ForumException {
		Boolean check = false;
		check = StringUtils.isEmpty(userName);
		check = check == true ? check : ValidationUtility.isNull(userEntity);
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserName());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserPswd());
		check = check == true ? check : userName.equals(userEntity.getUserName()) == false;
		if(check) {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
}
