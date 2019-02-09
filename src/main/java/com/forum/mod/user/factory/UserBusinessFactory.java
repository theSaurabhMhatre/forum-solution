package com.forum.mod.user.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forum.app.constant.ForumError;
import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.app.util.LikeUtility;
import com.forum.app.util.StringUtility;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerService;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.service.UserEntity;
import com.forum.mod.user.service.UserService;

public class UserBusinessFactory {
	private static final String USER_NAME = "name";
	private static final String USER_PSWD = "pswd";
	private static final String USER_BIO = "bio";
	private static final String USER_SECRET = "secret";
	
	private QuestionService questionService;
	private UserService userService;
	private AnswerService ansService;

	public UserBusinessFactory(QuestionService questionService,
			AnswerService ansService, UserService userService) {
		this.questionService = questionService;
		this.ansService = ansService;
		this.userService = userService;
	}
	
	public UserEntity getUser(Long userId) throws ForumException {
		if (userId != null) {
			UserEntity userEntity = null;
			userEntity = userService.getUser(userId);
			UserEntity userView = null;
			try {
				userView = (UserEntity) userEntity.clone();
				//uncomment if password is to be sent in response
				/*
				String encodedPassword = userView.getUserPswd();
				String decodedPassword = StringUtility
						.decodeString(encodedPassword);
				userView.setUserPswd(decodedPassword);
				*/
				userView.setUserPswd(USER_SECRET);
			} catch (CloneNotSupportedException ex) {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
			return userView;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public UserEntity addUser(UserEntity userEntity) throws ForumException {
		if (userEntity != null && userEntity.getUserId() == null) {
			String password = userEntity.getUserPswd();
			String encodedPassword = StringUtility.encodeString(password);
			userEntity.setUserPswd(encodedPassword);
			userEntity = userService.modifyUser(userEntity);
			UserEntity userView = null;
			try {
				userView = (UserEntity) userEntity.clone();
				//uncomment if password is to be sent in response
				/*
				encodedPassword = userView.getUserPswd();
				String decodedPassword = StringUtility
						.decodeString(encodedPassword);
				userView.setUserPswd(decodedPassword);
				*/
				userView.setUserPswd(USER_SECRET);
			} catch (CloneNotSupportedException ex) {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
			return userView;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public UserEntity performUpdate(Long userId, UserEntity userEntity,
			String attribute) throws ForumException {
		UserEntity currentUserEntity = userService.getUser(userId);
		switch(attribute) {
			case USER_NAME: 
				currentUserEntity.setUserName(userEntity.getUserName());
				break;
			case USER_PSWD: 							
				String password = userEntity.getUserPswd();
				String encodedPassword = StringUtility.encodeString(password);
				currentUserEntity.setUserPswd(encodedPassword);
				break;
			case USER_BIO:	
				currentUserEntity.setUserBio(userEntity.getUserBio());
				break;
			default:
				throw new ForumException(ForumError.MODIFY_ERROR.getMessage());
		}
		userEntity = userService.modifyUser(currentUserEntity);
		return userEntity;
	}
	
	public UserEntity updateUser(Long userId, UserEntity userEntity, String attribute)
			throws ForumException {
		if (userId != null && userEntity != null
				&& userId.equals(userEntity.getUserId())) {
			if(attribute != null && (attribute.equals(USER_NAME) ||
					attribute.equals(USER_PSWD) || attribute.equals(USER_BIO))) {
				userEntity = performUpdate(userId, userEntity, attribute);
				UserEntity userView = null;
				try {
					userView = (UserEntity) userEntity.clone();
					//uncomment if password is to be sent in response
					/*
					String encodedPassword = userView.getUserPswd();
					String decodedPassword = StringUtility
							.decodeString(encodedPassword);
					userView.setUserPswd(decodedPassword);
					*/
					userView.setUserPswd(USER_SECRET);
				} catch (CloneNotSupportedException ex) {
					throw new ForumException(
							ForumValidation.VALIDATION_FAILURE.getMessage());
				}
				return userView;
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public void deleteUser(Long userId) throws ForumException {
		if (userId != null) {
			// all answer likes by user
			ansService.deleteAnsLikesByUserId(userId);
			// all question likes by user
			questionService.deleteQuesLikesByUserId(userId);
			// all answered answered by user, likes for those answers
			List<AnswerEntity> answers = ansService.getAnswersByUser(userId);
			for (AnswerEntity answer : answers) {
				Long ansId = answer.getAnsId();
				ansService.deleteAnsLikesByAnsId(ansId);
				ansService.deleteAnswer(ansId);
			}
			// all questions asked by user, likes for those questions
			// answers for those questions
			// and the likes for those answers
			List<QuestionEntity> questions = questionService
					.getQuestionsByUser(userId);
			for (QuestionEntity question : questions) {
				Long quesId = question.getQuesId();
				List<AnswerEntity> tempAnswers = ansService
						.getAnswersByQuestion(quesId);
				for (AnswerEntity ans : tempAnswers) {
					Long ansId = ans.getAnsId();
					ansService.deleteAnsLikesByAnsId(ansId);
					ansService.deleteAnswer(ansId);
				}
				questionService.deleteQuesLikesByQuesId(quesId);
				questionService.deleteQuestion(quesId);
			}
			// ansService.deleteAnsByUserId(userId);
			// questionService.deleteQuesByUserId(userId);
			userService.deleteUser(userId);
			return;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public UserEntity validateUser(String userName, UserEntity userEntity)
			throws ForumException {
		if (userName != null && userEntity != null
				&& userName.equals(userEntity.getUserName())) {
			String password = userEntity.getUserPswd();
			String encodedPassword = StringUtility.encodeString(password);
			userEntity.setUserPswd(encodedPassword);
			userEntity = userService.validateUser(userEntity);
			UserEntity userView = null;
			try {
				userView = (UserEntity) userEntity.clone();
				//uncomment if password is to be sent in response
				/*
				encodedPassword = userView.getUserPswd();
				String decodedPassword = StringUtility
						.decodeString(encodedPassword);
				userView.setUserPswd(decodedPassword);
				*/
				userView.setUserPswd(USER_SECRET);
			} catch (CloneNotSupportedException ex) {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
			return userView;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public List<List<Object>> getUserRankings() throws ForumException {
		List<Long> userIds = userService.getUserIds();
		List<Object> users = userService.getAllUsers();
		Map<Long, String> usersMap = new HashMap<Long, String>();
		for(Object currentUser : users) {
			Object[] tempUser = (Object[]) currentUser;
			usersMap.put((Long) tempUser[0], (String) tempUser[1]);
		}
		List<Object> ansLikesByUsers = ansService.getAllUsersAnswersLikes();
		List<Object> quesLikesByUsers = questionService.getAllUsersQuestionsLikes();
		Map<Long, Long> ansLikes = LikeUtility.normalizeLikes(ansLikesByUsers, userIds);
		Map<Long, Long> quesLikes = LikeUtility.normalizeLikes(quesLikesByUsers, userIds);
		List<List<Object>> userRankings = new ArrayList<List<Object>>();
		Integer index = 0;
		for(Long userId : userIds) {
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

}
