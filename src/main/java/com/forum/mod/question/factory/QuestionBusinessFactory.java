package com.forum.mod.question.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.FetchException;
import com.forum.app.exception.ForumException;
import com.forum.app.key.QuestionLikeKey;
import com.forum.app.util.SortUtility;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerService;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.service.UserEntity;
import com.forum.mod.user.service.UserService;

public class QuestionBusinessFactory {
	private static final String TYPE_QUESTION = "question";
	private static final String TYPE_ANSWER = "answer";
	private static final String TYPE_ALL = "all";
	private static final String CAT_TECHNOLOGY = "technology";
	private static final String CAT_SCIENCE = "science";
	private static final String CAT_HISTORY = "history";
	private static final String CAT_COMICS = "comics";
	private static final String CAT_OTHERS = "others";
	private static final String CAT_ALL = "all";
	private static final String NO_ANSWER = "This question has not been answered yet!";
	
	private QuestionService questionService;
	private UserService userService;
	private AnswerService ansService;

	public QuestionBusinessFactory() {
		super();
	}
	
	public QuestionBusinessFactory(QuestionService questionService,
			AnswerService ansService, UserService userService) {
		this.questionService = questionService;
		this.ansService = ansService;
		this.userService = userService;
	}

	public List<Object> getMostLikedAnsByQuestions(List<Long> quesIds)
			throws FetchException {
		List<Object> ansByQuestionsTemp = ansService
				.getMostLikedAnsByQuestions();
		List<Object> ansByQuestions = new ArrayList<Object>();
		outer: for (Long quesId : quesIds) {
			for (Object current : ansByQuestionsTemp) {
				Object[] currentRec = (Object[]) current;
				String temp = currentRec[1].toString();
				Long currQuesId = Long.parseLong(temp);
				if (quesId.equals(currQuesId)) {
					temp = currentRec[0].toString();
					Long currAnsId = Long.parseLong(temp);
					temp = currentRec[2].toString();
					Long currLikes = Long.parseLong(temp);
					AnswerEntity ans = ansService.getAnswer(currAnsId);
					ans.setAnsweredBy(ans.getUser().getUserId());
					ans.setQuesId(ans.getQuestion().getQuesId());
					ans.setLikes(currLikes);
					ansByQuestions.add(ans);
					continue outer;
				}
			}
			// current question has no answer
			// or answer(s) do(es) not have any likes
			// fetch answer separately
			try {
				AnswerEntity ans = ansService.getAnsByQuesId(quesId);
				ans.setAnsweredBy(ans.getUser().getUserId());
				ans.setQuesId(ans.getQuestion().getQuesId());
				ans.setLikes(0L);
				ansByQuestions.add(ans);
			} catch (ForumException ex) {
				AnswerEntity ans = new AnswerEntity();
				ans.setAnsId(0L);
				ans.setAns(NO_ANSWER);
				ans.setAnsweredBy(0L);
				ans.setQuesId(0L);
				ans.setLikes(0L);
				ansByQuestions.add(ans);
			}
		}
		return ansByQuestions;
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
			throws FetchException {
		// default values to be used when fetching all questions
		String keyword = "%%";
		String category = "%%";
		if(searchCriteria.length > 0) {
			keyword = "%" + searchCriteria[0] + "%";
			category = searchCriteria[1].equals(CAT_ALL) ? category : searchCriteria[1];
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
		List<Object> answersList = getMostLikedAnsByQuestions(quesIds);
		List<Object> result = new ArrayList<Object>();
		result.add(questionsList);
		result.add(answersList);
		return result;
	}

	public QuestionEntity addQuestion(QuestionEntity question)
			throws ForumException {
		if (question != null && question.getQuesId() == null) {
			Long userId = question.getAskedBy();
			if (userId != null) {
				UserEntity user = userService.getUser(userId);
				question.setUser(user);
				question.setLikes(0L);
				question = questionService.modifyQuestion(question);
				return question;
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public QuestionEntity updateQuestion(Long quesId, QuestionEntity question)
			throws ForumException {
		if (question != null && quesId != null
				&& quesId.equals(question.getQuesId())) {
			Long userId = question.getAskedBy();
			if (userId != null) {
				UserEntity user = userService.getUser(userId);
				question.setUser(user);
				question = questionService.modifyQuestion(question);
				Long likes = questionService.getLikesByQuestion(quesId);
				question.setLikes(likes);
				return question;
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public void deleteQuestion(Long quesId) throws ForumException {
		if (quesId != null) {
			ansService.deleteAnsLikesByQuesId(quesId);
			ansService.deleteAnsByQuesId(quesId);
			questionService.deleteQuesLikesByQuesId(quesId);
			questionService.deleteQuestion(quesId);
			return;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public QuestionLikeEntity likeQuestion(Long quesId,
			QuestionLikeEntity quesLike) throws ForumException {
		if (quesId != null && quesLike != null
				&& quesId.equals(quesLike.getQuesId())) {
			Long userId = quesLike.getUserId();
			if (quesId != null && userId != null) {
				QuestionEntity ques = questionService.getQuestion(quesLike
						.getQuesId());
				UserEntity user = userService.getUser(userId);
				QuestionLikeKey key = new QuestionLikeKey();
				key.setQuestion(ques);
				key.setUser(user);
				quesLike.setQuesLikeKey(key);
				key = questionService.likeQuestion(quesLike);
				quesLike.setQuesId(key.getQuestion().getQuesId());
				quesLike.setUserId(key.getUser().getUserId());
				return quesLike;
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public List<Integer> getQuestionsLikedByUser(Long userId)
			throws ForumException {
		if (userId != null) {
			List<Integer> questionLikes = questionService
					.getQuestionsLikedByUser(userId);
			return questionLikes;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public Long getUsersQuestionsLikes(Long userId)
			throws ForumException {
		if (userId != null) {
			Long totalLikes = questionService
					.getUsersQuestionsLikes(userId);
			return totalLikes;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public List<QuestionEntity> getQuestionsByUser(Long userId)
			throws ForumException {
		if(userId != null) {
			List<QuestionEntity> questions = questionService
					.getQuestionsByUser(userId);
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
			return questions;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public List<QuestionEntity> getQuestionsAnsweredByUser(Long userId) 
			throws ForumException {
		if(userId != null) {
			List<Long> quesIds = new ArrayList<Long>();
			List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
			List<AnswerEntity> answers = ansService.getAnswersByUser(userId);
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
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());			
		}
	}
	
	public List<Object> getAnswersByCriteria(String...searchCriteria) 
			throws FetchException{
		// default values to be used when fetching all answers
		String keyword = "%%";
		String category = "%%";
		if(searchCriteria.length > 0) {
			keyword = "%" + searchCriteria[0] + "%";
			category = searchCriteria[1].equals(CAT_ALL) ? category : searchCriteria[1];
		}
		List<Long> quesIds = new ArrayList<Long>();
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
		List<AnswerEntity> answers = ansService.getAnswers(keyword, category);
		List<Object> ansLikes = ansService.getLikesByAnswers(keyword, category);
		for(AnswerEntity ans : answers) {
			QuestionEntity ques = ans.getQuestion();
			UserEntity user = ans.getUser();
			ans.setAnsweredBy(user.getUserId());
			ans.setQuesId(ques.getQuesId());
			questions.add(ques);
			quesIds.add(ques.getQuesId());
			for(Object ansLike : ansLikes) {
				Object[] currentAnsLike = (Object[]) ansLike;
				Long ansId = (Long) currentAnsLike[1];
				if(ans.getAnsId().equals(ansId)) {
					Long likes = (Long) currentAnsLike[0];
					ans.setLikes(likes);
					break;
				}
				ans.setLikes(0L);
			}
			if(ans.getLikes() == null) {
				ans.setLikes(0L);
			}
		}
		Set<AnswerEntity> sortedAnswers = SortUtility.sortAnswers(answers);
		List<Object> answersList = Arrays.asList(sortedAnswers.toArray());
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
		List<QuestionEntity> questionsList = new ArrayList<QuestionEntity>();
		for(Object answer : answersList) {
			AnswerEntity current = (AnswerEntity) answer;
			for(QuestionEntity ques : questions) {
				Long quesId = ques.getQuesId();
				if(current.getQuesId().equals(quesId)) {
					questionsList.add(ques);
				}
			}
		}
		List<Object> result = new ArrayList<Object>();
		result.add(questionsList);
		result.add(answersList);
		return result;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Object> getSearchResults(String type, String category, String keyword) 
			throws ForumException {
		if(type != null && category != null && keyword != null 
				&& (category.equals(CAT_TECHNOLOGY) || category.equals(CAT_SCIENCE) 
						|| category.equals(CAT_HISTORY) || category.equals(CAT_COMICS)
						|| category.equals(CAT_OTHERS) || category.equals(CAT_ALL)) ) {
			List<Object> searchResults = new ArrayList<Object>();
			Map<String, Integer> count = new HashMap<String, Integer>();
			String[] searchCriteria = {keyword, category, type};
			switch(type) {
				case TYPE_QUESTION:
					searchResults = getQuestionsWithMostLikedAns(searchCriteria);
					count.put("questions",((List<QuestionEntity>)searchResults.get(0)).size());
					count.put("answers", 0);
					break;
				case TYPE_ANSWER:
					searchResults = getAnswersByCriteria(searchCriteria);
					count.put("answers",((List<AnswerEntity>)searchResults.get(0)).size());
					count.put("questions", 0);
					break;
				case TYPE_ALL:
					List<QuestionEntity> questionsList = new ArrayList<QuestionEntity>();
					List<AnswerEntity> answersList = new ArrayList<AnswerEntity>();
					searchResults = getQuestionsWithMostLikedAns(searchCriteria);
					count.put("questions",((List<QuestionEntity>)searchResults.get(0)).size());
					List<QuestionEntity> tempQuesList = (List<QuestionEntity>) searchResults.get(0);
					questionsList.addAll(tempQuesList);
					List<AnswerEntity> tempAnsList = (List<AnswerEntity>) searchResults.get(1);
					answersList.addAll(tempAnsList);
					searchResults.clear();
					searchResults = getAnswersByCriteria(searchCriteria);
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
				default:
					throw new ForumException(
							ForumValidation.VALIDATION_FAILURE.getMessage());
			}
			searchResults.add(count);
			return searchResults;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

}
