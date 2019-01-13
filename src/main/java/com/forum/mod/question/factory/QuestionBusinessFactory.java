package com.forum.mod.question.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.FetchException;
import com.forum.app.exception.ForumException;
import com.forum.app.key.QuestionLikeKey;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerService;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.service.UserEntity;
import com.forum.mod.user.service.UserService;

public class QuestionBusinessFactory {
	private QuestionService questionService;
	private UserService userService;
	private AnswerService ansService;

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
				ans.setAns("NOANSWER");
				ans.setAnsweredBy(0L);
				ans.setQuesId(0L);
				ans.setLikes(0L);
				ansByQuestions.add(ans);
			}
		}
		return ansByQuestions;
	}

	public Set<QuestionEntity> sortQuestions(List<QuestionEntity> questions) {
		Set<QuestionEntity> sortedQuestions = new TreeSet<QuestionEntity>(
				new Comparator<QuestionEntity>() {
					public int compare(QuestionEntity quesOne,
							QuestionEntity quesTwo) {
						Long one = quesOne.getLikes();
						Long two = quesTwo.getLikes();
						if (one > two) {
							return -1;
						} else {
							return 1;
						}
					}
				});
		for (QuestionEntity ques : questions) {
			sortedQuestions.add(ques);
		}
		return sortedQuestions;
	}

	public List<Object> getQuestionsWithMostLikedAns() throws FetchException {
		List<QuestionEntity> questions = questionService.getQuestions();
		List<Object> quesLikes = questionService.getLikesByQuestions();
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
		Set<QuestionEntity> sortedQuestions = sortQuestions(questions);
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

	// set likes
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

}
