package com.forum.mod.question.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.forum.app.exception.ForumException;
import com.forum.app.key.QuestionLikeKey;
import com.forum.app.util.SortUtility;
import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionEntity.Category;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.factory.UserValidationFactory;
import com.forum.mod.user.service.UserEntity;

/**
 * This class performs all the business logic before and/or after storing
 * and retrieving data to and from the database for QuestionEntity and
 * QuestionLikeEntity objects. All other business factories communicate with
 * QuestionService via this class.
 * 
 * @author Saurabh Mhatre
 *
 */
public class QuestionBusinessFactory {
	private QuestionService questionService;
	private UserBusinessFactory userBusinessFactory;
	private AnswerBusinessFactory ansBusinessFactory;

	public QuestionBusinessFactory() {
		questionService = new QuestionService();
	}

	public void setBusinessFactories(UserBusinessFactory userBusinessFactory,
			AnswerBusinessFactory ansBusinessFactory) {
		this.userBusinessFactory = userBusinessFactory;
		this.ansBusinessFactory = ansBusinessFactory;
	}

	public List<QuestionEntity> removeDuplicateQuestions(List<QuestionEntity> questions) {
		Map<Long, QuestionEntity> questionsMap = new HashMap<Long, QuestionEntity>();
		for (QuestionEntity ques : questions) {
			if (questionsMap.containsKey(ques.getQuesId()) == false) {
				questionsMap.put(ques.getQuesId(), ques);
			}
		}
		List<QuestionEntity> distinctQuestions = new ArrayList<QuestionEntity>(questionsMap.values());
		return distinctQuestions;
	}

	public QuestionEntity getQuestion(Long quesId) throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		QuestionEntity question = questionService.getQuestion(quesId);
		question.setAskedBy(question.getUser().getUserId());
		List<Object> quesLikes = getLikesBySpecificQuestions(Arrays.asList(question.getQuesId()));
		if (quesLikes.size() > 0) {
			Object[] quesLike = (Object[]) quesLikes.get(0);
			Long likes = (Long) quesLike[0];
			question.setLikes(likes);
		} else {
			question.setLikes(0L);
		}
		return question;
	}

	public List<Object> getQuestionsWithMostLikedAns(String... searchCriteria) throws ForumException {
		QuestionValidationFactory.validateSearchCriteria(searchCriteria);
		// default values to be used when fetching all questions
		String keyword = "%%";
		String category = "%%";
		if (searchCriteria.length > 0) {
			keyword = "%" + searchCriteria[0] + "%";
			category = searchCriteria[1].equals(Category.ALL.getCategory()) ? category : searchCriteria[1];
		}
		List<QuestionEntity> questions = questionService.getQuestions(keyword, category);
		List<Object> quesLikes = questionService.getLikesByQuestions(keyword, category);
		for (QuestionEntity ques : questions) {
			ques.setAskedBy(ques.getUser().getUserId());
			for (Object quesLike : quesLikes) {
				Object[] currentQuesLike = (Object[]) quesLike;
				Long quesId = (Long) currentQuesLike[1];
				if (ques.getQuesId().equals(quesId)) {
					Long likes = (Long) currentQuesLike[0];
					ques.setLikes(likes);
					break;
				}
				ques.setLikes(0L);
			}
			if (ques.getLikes() == null) {
				ques.setLikes(0L);
			}
		}
		Set<QuestionEntity> sortedQuestions = SortUtility.sortQuestions(questions);
		List<Object> questionsList = Arrays.asList(sortedQuestions.toArray());
		List<Long> quesIds = new ArrayList<Long>();
		for (Object current : questionsList) {
			QuestionEntity ques = (QuestionEntity) current;
			quesIds.add(ques.getQuesId());
		}
		List<Object> answersList = ansBusinessFactory.getMostLikedAnsByQuestions(quesIds);
		List<Object> result = new ArrayList<Object>();
		result.add(questionsList);
		result.add(answersList);
		return result;
	}

	public QuestionEntity addQuestion(QuestionEntity question) throws ForumException {
		QuestionValidationFactory.validateAddQuestion(question);
		Long userId = question.getAskedBy();
		UserEntity user = userBusinessFactory.getUser(userId);
		question.setUser(user);
		question.setLikes(0L);
		question.setQuesCreatedOn(new Date());
		question.setQuesModifiedOn(new Date());
		question = questionService.modifyQuestion(question);
		return question;
	}

	public QuestionEntity updateQuestion(Long quesId, QuestionEntity question) throws ForumException {
		QuestionValidationFactory.validateUpdateQuestion(quesId, question);
		Long userId = question.getAskedBy();
		UserEntity user = userBusinessFactory.getUser(userId);
		question.setUser(user);
		question.setQuesModifiedOn(new Date());
		question = questionService.modifyQuestion(question);
		Long likes = questionService.getLikesByQuestion(quesId);
		question.setLikes(likes);
		return question;
	}

	public void deleteQuestion(Long quesId) throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		ansBusinessFactory.deleteAnswerByQuesId(quesId);
		questionService.deleteQuesLikesByQuesId(quesId);
		questionService.deleteQuestion(quesId);
	}

	public QuestionLikeEntity likeQuestion(Long quesId, QuestionLikeEntity quesLike) throws ForumException {
		QuestionValidationFactory.validateLikeOperation(quesId, quesLike);
		Long userId = quesLike.getUserId();
		QuestionEntity ques = questionService.getQuestion(quesLike.getQuesId());
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionLikeKey key = new QuestionLikeKey();
		key.setQuestion(ques);
		key.setUser(user);
		quesLike.setQuesLikeKey(key);
		quesLike.setQuesLikedOn(new Date());
		key = questionService.likeQuestion(quesLike);
		quesLike.setQuesId(key.getQuestion().getQuesId());
		quesLike.setUserId(key.getUser().getUserId());
		return quesLike;
	}

	public void dislikeQuestion(Long quesId, QuestionLikeEntity quesLike) throws ForumException {
		QuestionValidationFactory.validateLikeOperation(quesId, quesLike);
		Long userId = quesLike.getUserId();
		QuestionEntity ques = questionService.getQuestion(quesLike.getQuesId());
		UserEntity user = userBusinessFactory.getUser(userId);
		QuestionLikeKey key = new QuestionLikeKey();
		key.setQuestion(ques);
		key.setUser(user);
		quesLike.setQuesLikeKey(key);
		questionService.dislikeQuestion(quesLike);
		return;
	}

	public List<Integer> getQuestionsLikedByUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Integer> questionLikes = questionService.getQuestionsLikedByUser(userId);
		return questionLikes;
	}

	public Long getUsersQuestionsLikes(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		Long totalLikes = questionService.getUsersQuestionsLikes(userId);
		return totalLikes;
	}

	public List<QuestionEntity> getQuestionsByUser(Long userId, Boolean withLikes) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<QuestionEntity> questions = questionService.getQuestionsByUser(userId);
		if (withLikes) {
			List<Object> quesLikes = questionService.getLikesByQuestionsByUser(userId);
			for (QuestionEntity ques : questions) {
				ques.setAskedBy(ques.getUser().getUserId());
				for (Object quesLike : quesLikes) {
					Object[] currentQuesLike = (Object[]) quesLike;
					Long quesId = (Long) currentQuesLike[1];
					if (ques.getQuesId().equals(quesId)) {
						Long likes = (Long) currentQuesLike[0];
						ques.setLikes(likes);
						break;
					}
					ques.setLikes(0L);
				}
				if (ques.getLikes() == null) {
					ques.setLikes(0L);
				}
			}
		}
		return questions;
	}

	public List<Object> getQuestionsAnsweredByUser(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Long> quesIds = new ArrayList<Long>();
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
		Map<Long, AnswerEntity> quesAnsMap = new HashMap<Long, AnswerEntity>();
		List<AnswerEntity> answers = ansBusinessFactory.getAnswersByUser(userId);
		for (AnswerEntity answer : answers) {
			UserEntity askedBy = answer.getQuestion().getUser();
			answer.getQuestion().setAskedBy(askedBy.getUserId());
			quesIds.add(answer.getQuestion().getQuesId());
			questions.add(answer.getQuestion());
			quesAnsMap.put(answer.getQuestion().getQuesId(), answer);
		}
		List<Object> quesLikes = questionService.getLikesBySpecificQuestions(quesIds);
		for (QuestionEntity ques : questions) {
			ques.setAskedBy(ques.getUser().getUserId());
			for (Object quesLike : quesLikes) {
				Object[] currentQuesLike = (Object[]) quesLike;
				Long quesId = (Long) currentQuesLike[1];
				if (ques.getQuesId().equals(quesId)) {
					Long likes = (Long) currentQuesLike[0];
					ques.setLikes(likes);
					break;
				}
				ques.setLikes(0L);
			}
			if (ques.getLikes() == null) {
				ques.setLikes(0L);
			}
		}
		answers.clear();
		List<QuestionEntity> distinctQuestions = removeDuplicateQuestions(questions);
		for (QuestionEntity question : distinctQuestions) {
			answers.add(quesAnsMap.get(question.getQuesId()));
		}
		List<Object> answeredQuestions = new ArrayList<Object>();
		answeredQuestions.add(distinctQuestions);
		answeredQuestions.add(answers);
		return answeredQuestions;
	}

	public List<Object> getAllUsersQuestionsLikes() throws ForumException {
		List<Object> quesLikesByUsers = questionService.getAllUsersQuestionsLikes();
		return quesLikesByUsers;
	}

	public void deleteQuesLikesByUserId(Long userId) throws ForumException {
		UserValidationFactory.validateUserId(userId);
		questionService.deleteQuesLikesByUserId(userId);
	}

	public List<Object> getLikesBySpecificQuestions(List<Long> quesIds) throws ForumException {
		for (Long quesId : quesIds) {
			QuestionValidationFactory.validateQuestionId(quesId);
		}
		List<Object> questionLikes = questionService.getLikesBySpecificQuestions(quesIds);
		return questionLikes;
	}

}
