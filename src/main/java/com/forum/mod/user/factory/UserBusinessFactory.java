package com.forum.mod.user.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.forum.app.exception.ForumException;
import com.forum.app.util.FileUtility;
import com.forum.app.util.LikeUtility;
import com.forum.app.util.StringUtility;
import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.factory.QuestionBusinessFactory;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.user.service.UserEntity;
import com.forum.mod.user.service.UserEntity.Attribute;
import com.forum.mod.user.service.UserService;

/**
 * This class performs all the business logic before and/or after storing
 * and retrieving data to and from the database for UserEntity objects.
 * All other business factories communicate with UserService via this class.
 * 
 * @author Saurabh Mhatre
 *
 */
public class UserBusinessFactory {
	private UserService userService;
	private AnswerBusinessFactory ansBusinessFactory;
	private QuestionBusinessFactory quesBusinessFactory;

	/**
	 * This is the default constructor, initializes UserService. This is the
	 * only place where UserService is initialized.
	 * 
	 */
	public UserBusinessFactory() {
		userService = new UserService();
	}

	/**
	 * This sets the other business factory objects to the respective instance
	 * variables which are then used to communicate with other modules. 
	 * 
	 * @param AnswerBusinessFactory ansBusinessFactory - answer factory to set.
	 * @param QuestionBusinessFactory quesBusinessFactory - question factory to set.
	 */
	public void setBusinessFactories(AnswerBusinessFactory ansBusinessFactory,
			QuestionBusinessFactory quesBusinessFactory) {
		this.ansBusinessFactory = ansBusinessFactory;
		this.quesBusinessFactory = quesBusinessFactory;
	}

	/**
	 * This fetches the user corresponding to the passed user Id.  
	 * 
	 * @param Long userId - Id of of the user to be fetched.
	 * @return UserEntity userView - user corresponding to userId.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public UserEntity getUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		UserEntity userEntity = null;
		userEntity = userService.getUser(userId);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		// uncomment if password is to be sent in response
		/*
		String encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}

	/**
	 * This adds a question to the DB if validation is successful.
	 * 
	 * @param UserEntity userEntity - object to be added. 
	 * @return UserEntity userView - added object.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public UserEntity addUser(UserEntity userEntity) throws ForumException {
		UserValidationFactory.validateAddUser(userEntity);
		String password = userEntity.getUserPswd();
		String encodedPassword = StringUtility.encodeString(password);
		userEntity.setUserPswd(encodedPassword);
		userEntity.setUserCreatedOn(new Date());
		userEntity.setUserModifiedOn(new Date());
		userEntity = userService.modifyUser(userEntity);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		// uncomment if password is to be sent in response
		/*
		encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}

	/**
	 * This modifies an existing question if validation is successful. It 
	 * modifies a single property or all the properties depending on the value
	 * of the passed attribute.
	 * 
	 * @param Long userId - Id of the user to be modified.
	 * @param UserEntity userEntity - object to be modified.
	 * @param String attribute - determines which property/properties to modify.
	 * @return UserEntity userView - modified object.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public UserEntity updateUser(Long userId, UserEntity userEntity, String attribute) throws ForumException {
		UserValidationFactory.validateUpdateUser(userEntity, userId, attribute);
		UserEntity currentUserEntity = userService.getUser(userId);
		Attribute attributeSwitch = Attribute.valueOf(attribute.toUpperCase());
		switch (attributeSwitch) {
		case USER_NAME:
			currentUserEntity.setUserName(userEntity.getUserName());
			break;
		case USER_MAIL:
			currentUserEntity.setUserMail(userEntity.getUserMail());
			break;
		case USER_BIO:
			currentUserEntity.setUserBio(userEntity.getUserBio());
			break;
		case USER_PSWD:
			String password = userEntity.getUserPswd();
			String encodedPassword = StringUtility.encodeString(password);
			currentUserEntity.setUserPswd(encodedPassword);
			break;
		case USER_SECRET:
			// updates all attributes, except password
			currentUserEntity.setUserName(userEntity.getUserName());
			currentUserEntity.setUserMail(userEntity.getUserMail());
			currentUserEntity.setUserBio(userEntity.getUserBio());
			break;
		case USER_AVATAR:
			// avatar update will be handled separately for now
			// adding this case here to get rid of warning
			break;
		}
		currentUserEntity.setUserModifiedOn(new Date());
		userEntity = userService.modifyUser(currentUserEntity);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		// uncomment if password is to be sent in response
		/*
		String encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}

	/**
	 * This deletes the user corresponding to the passed user Id. It also
	 * deletes all the questions asked by the user, the answers for those 
	 * questions and the likes the questions and the answers have. All the 
	 * answers submitted by the user and the likes corresponding to those 
	 * answers are also deleted.
	 * 
	 * @param Long userId - Id of the user to be deleted.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public void deleteUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		UserEntity userEntity = getUser(userId);
		// all answer likes by user
		ansBusinessFactory.deleteAnsLikesByUserId(userId);
		// all question likes by user
		quesBusinessFactory.deleteQuesLikesByUserId(userId);
		// all answered answered by user, likes for those answers
		List<AnswerEntity> answers = ansBusinessFactory.getAnswersByUser(userId);
		for (AnswerEntity answer : answers) {
			Long ansId = answer.getAnsId();
			ansBusinessFactory.deleteAnswer(ansId);
		}
		// all questions asked by user, likes for those questions
		// answers for those questions
		// and the likes for those answers
		Boolean withLikes = false;
		List<QuestionEntity> questions = quesBusinessFactory.getQuestionsByUser(userId, withLikes);
		for (QuestionEntity question : questions) {
			Long quesId = question.getQuesId();
			quesBusinessFactory.deleteQuestion(quesId);
		}
		// delete user avatar
		String fileName =userEntity.getUserAvatar(); 
		if(fileName != null) {
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
			FileUtility.deleteFile(fileName);
		}
		// delete user
		userService.deleteUser(userId);
		return;
	}

	/**
	 * This validates the existence of the passed user.
	 * 
	 * @param String userName - the name of the user to be validated.
	 * @param UserEntity userEntity - object containing validation information.
	 * @return UserEntity userView - user object, if passed information is valid.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public UserEntity validateUser(String userName, UserEntity userEntity) throws ForumException {
		UserValidationFactory.validateValidateUser(userName, userEntity);
		String password = userEntity.getUserPswd();
		String encodedPassword = StringUtility.encodeString(password);
		userEntity.setUserPswd(encodedPassword);
		userEntity = userService.validateUser(userEntity);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		// uncomment if password is to be sent in response
		/*
		encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}

	/**
	 * This checks if the passed user name exists.
	 * 
	 * @param String userName - name to be checked for availability.
	 * @return Map<String, Object> userAvailable - user object, if userName exists.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public Map<String, Object> checkAvailability(String userName) throws ForumException {
		UserValidationFactory.validateUserName(userName);
		UserEntity userEntity = userService.checkAvailability(userName.toLowerCase());
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		// uncomment if password is to be sent in response
		/*
		String encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		Map<String, Object> userAvailable = new HashMap<String, Object>();
		userAvailable.put("available", true);
		userAvailable.put("user", userView);
		return userAvailable;
	}

	/**
	 * This fetches the total likes received by all users for the questions
	 * asked and/or answers answered by those users. It then sorts the entries 
	 * in the descending order of total likes received by each user, which is 
	 * then returned as userRankings object.
	 * 
	 * @return List<List<Object>> userRankings - object containing ranking information.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public List<List<Object>> getUserRankings() throws ForumException {
		List<Long> userIds = userService.getUserIds();
		List<Object> users = userService.getAllUsers();
		Map<Long, String> usersMap = new HashMap<Long, String>();
		for (Object currentUser : users) {
			Object[] tempUser = (Object[]) currentUser;
			usersMap.put((Long) tempUser[0], (String) tempUser[1]);
		}
		List<Object> ansLikesByUsers = ansBusinessFactory.getAllUsersAnswersLikes();
		List<Object> quesLikesByUsers = quesBusinessFactory.getAllUsersQuestionsLikes();
		Map<Long, Long> ansLikes = LikeUtility.normalizeLikes(ansLikesByUsers, userIds);
		Map<Long, Long> quesLikes = LikeUtility.normalizeLikes(quesLikesByUsers, userIds);
		List<List<Object>> userRankings = new ArrayList<List<Object>>();
		Integer index = 0;
		for (Long userId : userIds) {
			userRankings.add(new ArrayList<Object>());
			userRankings.get(index).add(usersMap.get(userId));
			userRankings.get(index).add(userId);
			userRankings.get(index).add(ansLikes.get(userId));
			userRankings.get(index).add(quesLikes.get(userId));
			userRankings.get(index).add(ansLikes.get(userId) + quesLikes.get(userId));
			index++;
		}
		LikeUtility.sortLikes(userRankings);
		return userRankings;
	}

	/**
	 * This is used to upload user profile pictures i.e., avatars. It is also
	 * used to update an existing user avatar in which case it deletes the existing
	 * avatar before uploading the new avatar.
	 * 
	 * @param String userName - name of the user corresponding to the avatar.
	 * @param InputStream inputStream - uploaded file input stream.
	 * @param FormDataContentDisposition fileDetails - uploaded file details.
	 * @return UserEntity userEntity - user object with the avatar URL.
	 * @param String mode - determines if a file is being uploaded for the first time.
	 * @throws ForumException exception - wrapped exception thrown during processing.
	 */
	public UserEntity uploadUserAvatar(String userName, InputStream inputStream,
			FormDataContentDisposition fileDetails, String mode) throws ForumException {
		UserEntity userEntity = userService.checkAvailability(userName);
		UserValidationFactory.validateUploadUserAvatar(userEntity, inputStream, fileDetails, mode);
		String fileName = userEntity.getUserId() + fileDetails.getFileName()
				.substring(fileDetails.getFileName().indexOf("."), fileDetails.getFileName().length());
		String previousFilePath = userEntity.getUserAvatar();
		String previousFileName = null;
		if(previousFilePath != null) {
			previousFileName = previousFilePath.substring(previousFilePath.lastIndexOf("/") + 1);
		}
		String userAvatar = FileUtility.uploadFile(inputStream,mode, fileName, previousFileName);
		userEntity.setUserAvatar(userAvatar);
		userEntity.setUserModifiedOn(new Date());
		userEntity = userService.modifyUser(userEntity);
		UserEntity userView = (UserEntity) userEntity.cloneUser();
		// uncomment if password is to be sent in response
		/*
		String encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}

}
