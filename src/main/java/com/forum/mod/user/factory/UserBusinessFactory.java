package com.forum.mod.user.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forum.app.exception.ForumException;
import com.forum.app.util.LikeUtility;
import com.forum.app.util.StringUtility;
import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.factory.QuestionBusinessFactory;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.user.service.UserEntity;
import com.forum.mod.user.service.UserEntity.Attribute;
import com.forum.mod.user.service.UserService;

public class UserBusinessFactory {
	private UserService userService;
	private AnswerBusinessFactory ansBusinessFactory;
	private QuestionBusinessFactory quesBusinessFactory;
	
	public UserBusinessFactory() {
		userService = new UserService();
	}
	
	public void setBusinessFactories(AnswerBusinessFactory ansBusinessFactory,
			QuestionBusinessFactory quesBusinessFactory) {
		this.ansBusinessFactory = ansBusinessFactory;
		this.quesBusinessFactory = quesBusinessFactory;
	}
	
	public UserEntity getUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		UserEntity userEntity = null;
		userEntity = userService.getUser(userId);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		//uncomment if password is to be sent in response
		/*
		String encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility
				.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}

	public UserEntity addUser(UserEntity userEntity) throws ForumException {
		UserValidationFactory.validateAddUser(userEntity);
		String password = userEntity.getUserPswd();
		String encodedPassword = StringUtility.encodeString(password);
		userEntity.setUserPswd(encodedPassword);
		userEntity = userService.modifyUser(userEntity);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		//uncomment if password is to be sent in response
		/*
		encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility
				.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}
	
	public UserEntity updateUser(Long userId, UserEntity userEntity, String attribute)
			throws ForumException {
		UserValidationFactory.validateUpdateUser(userEntity, userId, attribute);
		UserEntity currentUserEntity = userService.getUser(userId);
		Attribute attributeSwitch = Attribute.valueOf(attribute.toUpperCase());
		switch(attributeSwitch) {
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
		}
		userEntity = userService.modifyUser(currentUserEntity);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		//uncomment if password is to be sent in response
		/*
		String encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility
				.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}

	public void deleteUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		// all answer likes by user
		ansBusinessFactory.deleteAnsLikesByUserId(userId);
		// ansService.deleteAnsLikesByUserId(userId);
		// all question likes by user
		quesBusinessFactory.deleteQuesLikesByUserId(userId);
		// quesService.deleteQuesLikesByUserId(userId);
		// all answered answered by user, likes for those answers
		List<AnswerEntity> answers = ansBusinessFactory.getAnswersByUser(userId);
		// List<AnswerEntity> answers = ansService.getAnswersByUser(userId);
		for (AnswerEntity answer : answers) {
			Long ansId = answer.getAnsId();
			ansBusinessFactory.deleteAnswer(ansId);
			// ansService.deleteAnsLikesByAnsId(ansId);
			// ansService.deleteAnswer(ansId);
		}
		// all questions asked by user, likes for those questions
		// answers for those questions
		// and the likes for those answers
		Boolean withLikes = false;
		List<QuestionEntity> questions = quesBusinessFactory
				.getQuestionsByUser(userId, withLikes);
		// List<QuestionEntity> questions = quesService.getQuestionsByUser(userId);
		for (QuestionEntity question : questions) {
			Long quesId = question.getQuesId();
			quesBusinessFactory.deleteQuestion(quesId);
			/*
			List<AnswerEntity> tempAnswers = ansService
					.getAnswersByQuestion(quesId);
			for (AnswerEntity ans : tempAnswers) {
				Long ansId = ans.getAnsId();
				ansService.deleteAnsLikesByAnsId(ansId);
				ansService.deleteAnswer(ansId);
			}
			quesService.deleteQuesLikesByQuesId(quesId);
			quesService.deleteQuestion(quesId);
			*/
		}
		// ansService.deleteAnsByUserId(userId);
		// questionService.deleteQuesByUserId(userId);
		userService.deleteUser(userId);
		return;
	}

	public UserEntity validateUser(String userName, UserEntity userEntity)
			throws ForumException {
		UserValidationFactory.validateValidateUser(userName, userEntity);
		String password = userEntity.getUserPswd();
		String encodedPassword = StringUtility.encodeString(password);
		userEntity.setUserPswd(encodedPassword);
		userEntity = userService.validateUser(userEntity);
		UserEntity userView = null;
		userView = (UserEntity) userEntity.cloneUser();
		//uncomment if password is to be sent in response
		/*
		encodedPassword = userView.getUserPswd();
		String decodedPassword = StringUtility
				.decodeString(encodedPassword);
		userView.setUserPswd(decodedPassword);
		*/
		userView.setUserPswd(Attribute.USER_SECRET.getAttribute());
		return userView;
	}
	
	public Boolean checkAvailability(String userName) throws ForumException {
		UserValidationFactory.validateUserName(userName);
		userService.checkAvailability(userName.toLowerCase());
		return true;
	}
	
	public List<List<Object>> getUserRankings() throws ForumException {
		List<Long> userIds = userService.getUserIds();
		List<Object> users = userService.getAllUsers();
		Map<Long, String> usersMap = new HashMap<Long, String>();
		for(Object currentUser : users) {
			Object[] tempUser = (Object[]) currentUser;
			usersMap.put((Long) tempUser[0], (String) tempUser[1]);
		}
		List<Object> ansLikesByUsers = ansBusinessFactory.getAllUsersAnswersLikes();
		// List<Object> ansLikesByUsers = ansService.getAllUsersAnswersLikes();
		List<Object> quesLikesByUsers = quesBusinessFactory.getAllUsersQuestionsLikes();
		// List<Object> quesLikesByUsers = quesService.getAllUsersQuestionsLikes();
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
