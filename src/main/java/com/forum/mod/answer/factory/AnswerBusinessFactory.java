package com.forum.mod.answer.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.forum.app.exception.FetchException;
import com.forum.app.exception.ForumException;
import com.forum.app.key.AnswerLikeKey;
import com.forum.app.util.SortUtility;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;
import com.forum.mod.answer.service.AnswerService;
import com.forum.mod.question.factory.QuestionBusinessFactory;
import com.forum.mod.question.factory.QuestionValidationFactory;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionEntity.Category;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.factory.UserValidationFactory;
import com.forum.mod.user.service.UserEntity;

public class AnswerBusinessFactory {
	private final String NO_ANSWER = "This question has not been answered yet!";
	
	private AnswerService ansService;
	private UserBusinessFactory userBusinessFactory;
	private QuestionBusinessFactory quesBusinessFactory;
	
	public AnswerBusinessFactory() {
		ansService = new AnswerService();
	}

	public void setBusinessFactories(UserBusinessFactory userBusinessFactory, 
			QuestionBusinessFactory quesBusinessFactory) {
		this.userBusinessFactory = userBusinessFactory;
		this.quesBusinessFactory = quesBusinessFactory;
	}
	
	public Set<AnswerEntity> getAnswersByQuestion(Long quesId)
			throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		List<AnswerEntity> answers = ansService
				.getAnswersByQuestion(quesId);
		List<Object> ansLikes = ansService
				.getLikesByAnswersByQuestion(quesId);
		for (AnswerEntity answer : answers) {
			answer.setQuesId(answer.getQuestion().getQuesId());
			answer.setAnsweredBy(answer.getUser().getUserId());
			for (Object ansLike : ansLikes) {
				Object[] currentAnsLike = (Object[]) ansLike;
				Long ansId = (Long) currentAnsLike[1];
				if (answer.getAnsId().equals(ansId)) {
					Long likes = (Long) currentAnsLike[0];
					answer.setLikes(likes);
					break;
				}
				answer.setLikes(0L);
			}
			if (answer.getLikes() == null) {
				answer.setLikes(0L);
			}
		}
		Set<AnswerEntity> sortedAnswers = SortUtility.sortAnswers(answers); 
		return sortedAnswers;
	}

	public AnswerEntity addAnswer(AnswerEntity answer) throws ForumException {
		AnswerValidationFactory.validateAddAnswer(answer);
		Long userId = answer.getAnsweredBy();
		Long quesId = answer.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		QuestionEntity question = quesBusinessFactory.getQuestion(quesId);
		// QuestionEntity question = quesService.getQuestion(quesId);
		answer.setUser(user);
		answer.setQuestion(question);
		answer.setLikes(0L);
		answer = ansService.modifyAnswer(answer);
		return answer;
	}

	public AnswerEntity updateAnswer(Long ansId, AnswerEntity answer)
			throws ForumException {
		AnswerValidationFactory.validateUpdateAnswer(ansId, answer);
		Long userId = answer.getAnsweredBy();
		Long quesId = answer.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		QuestionEntity question = quesBusinessFactory.getQuestion(quesId);
		// QuestionEntity question = quesService.getQuestion(quesId);
		answer.setUser(user);
		answer.setQuestion(question);
		answer = ansService.modifyAnswer(answer);
		Long likes = ansService.getLikesByAnswer(ansId);
		answer.setLikes(likes);
		return answer;
	}

	public void deleteAnswer(Long ansId) throws ForumException {
		AnswerValidationFactory.validateAnswerId(ansId);
		ansService.deleteAnsLikesByAnsId(ansId);
		ansService.deleteAnswer(ansId);
	}

	public AnswerLikeEntity likeAnswer(Long ansId, AnswerLikeEntity ansLike)
			throws ForumException {
		AnswerValidationFactory.validateLikeOperation(ansId, ansLike);
		Long userId = ansLike.getUserId();
		Long quesId = ansLike.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		QuestionEntity ques = quesBusinessFactory.getQuestion(quesId);
		// QuestionEntity ques = quesService.getQuestion(quesId);
		AnswerEntity ans = ansService.getAnswer(ansLike.getAnsId());
		AnswerLikeKey key = new AnswerLikeKey();
		key.setUser(user);
		key.setQuestion(ques);
		key.setAnswer(ans);
		ansLike.setAnsLikeKey(key);
		key = ansService.likeAnswer(ansLike);
		ansLike.setUserId(key.getUser().getUserId());
		ansLike.setQuesId(key.getQuestion().getQuesId());
		ansLike.setAnsId(key.getAnswer().getAnsId());
		return ansLike;
	}

	public void dislikeAnswer(Long ansId, AnswerLikeEntity ansLike)
			throws ForumException {
		AnswerValidationFactory.validateLikeOperation(ansId, ansLike);
		Long userId = ansLike.getUserId();
		Long quesId = ansLike.getQuesId();
		UserEntity user = userBusinessFactory.getUser(userId);
		// UserEntity user = userService.getUser(userId);
		QuestionEntity ques = quesBusinessFactory.getQuestion(quesId);
		// QuestionEntity ques = quesService.getQuestion(quesId);
		AnswerEntity ans = ansService.getAnswer(ansLike.getAnsId());
		AnswerLikeKey key = new AnswerLikeKey();
		key.setUser(user);
		key.setQuestion(ques);
		key.setAnswer(ans);
		ansLike.setAnsLikeKey(key);
		ansService.dislikeAnswer(ansLike);
		return;
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
	
	public List<Object> getAnswersByCriteria(String...searchCriteria) 
			throws ForumException {
		AnswerValidationFactory.validateSearchCriteria(searchCriteria);
		// default values to be used when fetching all answers
		String keyword = "%%";
		String category = "%%";
		if(searchCriteria.length > 0) {
			keyword = "%" + searchCriteria[0] + "%";
			category = searchCriteria[1].equals(Category.ALL.getCategory())
					? category : searchCriteria[1];
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
		List<Object> quesLikes = quesBusinessFactory.getLikesBySpecificQuestions(quesIds);
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
					break;
				}
			}
		}
		List<Object> result = new ArrayList<Object>();
		result.add(questionsList);
		result.add(answersList);
		return result;
	}	
	
	public List<Integer> getAnswersLikedByUser(Long userId)
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<Integer> answerLikes = ansService
				.getAnswersLikedByUser(userId);
		return answerLikes;
	}
	
	public Long getUsersAnswersLikes(Long userId)
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		Long totalLikes = ansService
				.getUsersAnswersLikes(userId);
		return totalLikes;
	}
	
	public List<Object> getAllUsersAnswersLikes()
			throws ForumException {
		List<Object> ansLikesByUsers = ansService
				.getAllUsersAnswersLikes();
		return ansLikesByUsers;
	}
	
	public List<AnswerEntity> getAnswersByUser(Long userId)
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		List<AnswerEntity> answers = ansService.getAnswersByUser(userId);
		return answers;
	}
	
	public void deleteAnsLikesByUserId(Long userId) 
			throws ForumException {
		UserValidationFactory.validateUserId(userId);
		ansService.deleteAnsLikesByUserId(userId);
	}
	
	public void deleteAnswerByQuesId(Long quesId) 
			throws ForumException {
		QuestionValidationFactory.validateQuestionId(quesId);
		ansService.deleteAnsLikesByQuesId(quesId);
		ansService.deleteAnsByQuesId(quesId);
	}
	
}
