package com.forum.mod.question.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.forum.app.exception.ForumException;
import com.forum.app.key.QuestionLikeKey;
import com.forum.app.util.SortUtility;
import com.forum.app.util.ValidationUtility.Type;
import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionEntity.Category;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.factory.UserValidationFactory;
import com.forum.mod.user.service.UserEntity;

public class QuestionBusinessFactory {
	private final String NO_ANSWER = "This question has not been answered yet!";
	
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
		for(QuestionEntity ques : questions) {
			if(questionsMap.containsKey(ques.getQuesId()) == false) {
				questionsMap.put(ques.getQuesId(), ques);
			}
		} 
		List<QuestionEntity> distinctQuestions = 
				new ArrayList<QuestionEntity>(questionsMap.values());
		return distinctQuestions;
	}
	
	public List<Object> getQuestionsWithMostLikedAns(String...searchCriteria) 
			throws ForumException {
		QuestionValidationFactory.validateSearchCriteria(searchCriteria);
		// default values to be used when fetching all questions
		String keyword = "%%";
		String category = "%%";
		if(searchCriteria.length > 0) {
			keyword = "%" + searchCriteria[0] + "%";
			category = searchCriteria[1].equals(Category.ALL.getCategory())
					? category : searchCriteria[1];
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

	public QuestionEntity addQuestion(QuestionEntity question)
			throws ForumException {
		QuestionValidationFactory.validateAddQuestion(question);
		Long userId = question.getAskedBy();
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		question.setUser(user);
		question.setLikes(0L);
		question = questionService.modifyQuestion(question);
		return question;
	}

	public QuestionEntity updateQuestion(Long quesId, QuestionEntity question)
			throws ForumException {
		QuestionValidationFactory.validateUpdateQuestion(quesId, question);
		Long userId = question.getAskedBy();
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		question.setUser(user);
		question = questionService.modifyQuestion(question);
		Long likes = questionService.getLikesByQuestion(quesId);
		question.setLikes(likes);
		return question;
	}

	public void deleteQuestion(Long quesId) throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		ansBusinessFactory.deleteAnswerByQuesId(quesId);
		// ansService.deleteAnsLikesByQuesId(quesId);
		// ansService.deleteAnsByQuesId(quesId);
		questionService.deleteQuesLikesByQuesId(quesId);
		questionService.deleteQuestion(quesId);
	}

	public QuestionLikeEntity likeQuestion(Long quesId,
			QuestionLikeEntity quesLike) throws ForumException {
		QuestionValidationFactory.validateLikeOperation(quesId, quesLike);
		Long userId = quesLike.getUserId();
		QuestionEntity ques = questionService.getQuestion(quesLike
				.getQuesId());
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		QuestionLikeKey key = new QuestionLikeKey();
		key.setQuestion(ques);
		key.setUser(user);
		quesLike.setQuesLikeKey(key);
		key = questionService.likeQuestion(quesLike);
		quesLike.setQuesId(key.getQuestion().getQuesId());
		quesLike.setUserId(key.getUser().getUserId());
		return quesLike;
	}

	public void dislikeQuestion(Long quesId,
			QuestionLikeEntity quesLike) throws ForumException {
		QuestionValidationFactory.validateLikeOperation(quesId, quesLike);
		Long userId = quesLike.getUserId();
		QuestionEntity ques = questionService.getQuestion(quesLike
				.getQuesId());
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		QuestionLikeKey key = new QuestionLikeKey();
		key.setQuestion(ques);
		key.setUser(user);
		quesLike.setQuesLikeKey(key);
		questionService.dislikeQuestion(quesLike);
		return;
	}	
	
	public List<Integer> getQuestionsLikedByUser(Long userId)
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Integer> questionLikes = questionService
				.getQuestionsLikedByUser(userId);
		return questionLikes;
	}
	
	public Long getUsersQuestionsLikes(Long userId)
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		Long totalLikes = questionService
				.getUsersQuestionsLikes(userId);
		return totalLikes;
	}
	
	public List<QuestionEntity> getQuestionsByUser(Long userId, Boolean withLikes)
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<QuestionEntity> questions = questionService
				.getQuestionsByUser(userId);
		if(withLikes) {
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
	
	public List<QuestionEntity> getQuestionsAnsweredByUser(Long userId) 
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Long> quesIds = new ArrayList<Long>();
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
		List<AnswerEntity> answers = ansBusinessFactory.getAnswersByUser(userId);
		// List<AnswerEntity> answers = ansService.getAnswersByUser(userId);
		for(AnswerEntity answer : answers) {
			UserEntity askedBy = answer.getQuestion().getUser();
			answer.getQuestion().setAskedBy(askedBy.getUserId());
			quesIds.add(answer.getQuestion().getQuesId());
			questions.add(answer.getQuestion());
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
		List<QuestionEntity> distinctQuestions = removeDuplicateQuestions(questions);
		return distinctQuestions;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Object> getSearchResults(String type, String category, String keyword) 
			throws ForumException {
		String[] searchCriteria = {keyword, category, type};
		QuestionValidationFactory.validateSearchCriteria(searchCriteria);
		List<Object> searchResults = new ArrayList<Object>();
		Map<String, Integer> count = new HashMap<String, Integer>();
		Type typeSwitch = Type.valueOf(type.toUpperCase()); 
		switch(typeSwitch) {
			case QUESTION:
				searchResults = getQuestionsWithMostLikedAns(searchCriteria);
				count.put("questions",((List<QuestionEntity>)searchResults.get(0)).size());
				count.put("answers", 0);
				break;
			case ANSWER:
				searchResults = ansBusinessFactory.getAnswersByCriteria(searchCriteria);
				count.put("answers",((List<AnswerEntity>)searchResults.get(0)).size());
				count.put("questions", 0);
				break;
			case ALL:
				List<QuestionEntity> questionsList = new ArrayList<QuestionEntity>();
				List<AnswerEntity> answersList = new ArrayList<AnswerEntity>();
				searchResults = getQuestionsWithMostLikedAns(searchCriteria);
				count.put("questions",((List<QuestionEntity>)searchResults.get(0)).size());
				List<QuestionEntity> tempQuesList = (List<QuestionEntity>) searchResults.get(0);
				questionsList.addAll(tempQuesList);
				List<AnswerEntity> tempAnsList = (List<AnswerEntity>) searchResults.get(1);
				answersList.addAll(tempAnsList);
				searchResults.clear();
				searchResults = ansBusinessFactory.getAnswersByCriteria(searchCriteria);
				count.put("answers",((List<AnswerEntity>)searchResults.get(0)).size());
				tempQuesList = (List<QuestionEntity>) searchResults.get(0);
				tempAnsList = (List<AnswerEntity>) searchResults.get(1);
				// excluding results already fetched
				Integer innerIndex = null;
				Integer outerIndex = null;
				Integer innerResults = questionsList.size();
				Integer outerResults = tempQuesList.size();
				for(outerIndex = 0; outerIndex < outerResults; outerIndex++) {
					for(innerIndex = 0; innerIndex < innerResults; innerIndex++) {
						if(answersList.get(innerIndex).getAns().equals(NO_ANSWER)) {
							continue;
						} else if(questionsList.get(innerIndex).getQuesId()
								.equals(tempQuesList.get(outerIndex).getQuesId()) && 
								answersList.get(innerIndex).getAnsId()
								.equals(tempAnsList.get(outerIndex).getAnsId())) {
							break;
						}
					}
					if(innerIndex.equals(innerResults)) {
						questionsList.add(tempQuesList.get(outerIndex));
						answersList.add(tempAnsList.get(outerIndex));
					}
				}
				searchResults.clear();
				searchResults.add(questionsList);
				searchResults.add(answersList);
				break;
		}
		searchResults.add(count);
		return searchResults;
	}
	
	public List<Object> getAllUsersQuestionsLikes()
			throws ForumException {
		List<Object> quesLikesByUsers = questionService.getAllUsersQuestionsLikes();
		return quesLikesByUsers;
	}	
	
	public void deleteQuesLikesByUserId(Long userId)
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		questionService.deleteQuesLikesByUserId(userId);
	}	
	
	public QuestionEntity getQuestion(Long quesId)
			throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		QuestionEntity question = questionService.getQuestion(quesId);
		return question;
	}
	
	public List<Object> getLikesBySpecificQuestions(List<Long> quesIds)
			throws ForumException {
		for(Long quesId : quesIds) {
			QuestionValidationFactory.validateQuestionId(quesId);
		}
		List<Object> questionLikes = questionService.getLikesBySpecificQuestions(quesIds);
		return questionLikes;
	}	
	
}
