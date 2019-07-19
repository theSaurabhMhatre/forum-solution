package com.forum.mod.user.factory;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.app.util.ValidationUtility;
import com.forum.app.util.FileUtility.Mode;
import com.forum.mod.user.service.UserEntity;

/**
 * This class performs validation of the input data received by the user APIs
 * before it is processed by the UserBusinessFactory, throws validation
 * exception if data is invalid.
 * 
 * @author Saurabh Mhatre
 *
 */
public class UserValidationFactory {

	public static void validateUserId(Long userId) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(userId);
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateUserName(String userName) throws ForumException {
		Boolean check = false;
		check = StringUtils.isEmpty(userName);
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
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
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateUpdateUser(UserEntity userEntity, Long userId, String attribute) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(userEntity);
		check = check == true ? check : ValidationUtility.isNull(userId);
		check = check == true ? check : ValidationUtility.isNull(attribute);
		check = check == true ? check : ValidationUtility.isNull(userEntity.getUserId());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserName());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserPswd());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserMail());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserBio());
		check = check == true ? check : userId.equals(userEntity.getUserId()) == false;
		check = check == true ? check : ValidationUtility.validateAttribute(attribute);
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateValidateUser(String userName, UserEntity userEntity) throws ForumException {
		Boolean check = false;
		check = StringUtils.isEmpty(userName);
		check = check == true ? check : ValidationUtility.isNull(userEntity);
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserName());
		check = check == true ? check : StringUtils.isEmpty(userEntity.getUserPswd());
		check = check == true ? check : userName.equals(userEntity.getUserName()) == false;
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public static void validateUploadUserAvatar(UserEntity userEntity, InputStream inputStream,
			FormDataContentDisposition fileDetails, String mode) throws ForumException {
		Boolean check = false;
		check = ValidationUtility.isNull(userEntity);
		check = check == true ? check : ValidationUtility.isNull(inputStream);
		check = check == true ? check : ValidationUtility.isNull(fileDetails);
		check = check == true ? check : ValidationUtility.validateMode(mode);
		check = Mode.valueOf(mode.toUpperCase()).equals(Mode.UPDATE) ? StringUtils.isEmpty(userEntity.getUserAvatar())
				: check;
		check = Mode.valueOf(mode.toUpperCase()).equals(Mode.ADD) ? StringUtils.isNotEmpty(userEntity.getUserAvatar())
				: check;
		if (check) {
			throw new ForumException(ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

}
