package com.forum.mod.answer.factory;

import java.util.List;
import java.util.Set;

import com.forum.app.constant.ForumValidation;
import com.forum.app.exception.ForumException;
import com.forum.app.key.AnswerLikeKey;
import com.forum.app.util.SortUtility;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;
import com.forum.mod.answer.service.AnswerService;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.service.UserEntity;
import com.forum.mod.user.service.UserService;

public class AnswerBusinessFactory {
	private QuestionService quesService;
	private AnswerService ansService;
	private UserService userService;

	public AnswerBusinessFactory() {
		super();
	}
	
	public AnswerBusinessFactory(QuestionService quesService,
			AnswerService ansService, UserService userService) {
		this.quesService = quesService;
		this.ansService = ansService;
		this.userService = userService;
	}

	public Set<AnswerEntity> getAnswersByQuestion(Long quesId)
			throws ForumException {
		if (quesId != null) {
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
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public AnswerEntity addAnswer(AnswerEntity answer) throws ForumException {
		if (answer != null && answer.getAnsId() == null) {
			Long userId = answer.getAnsweredBy();
			Long quesId = answer.getQuesId();
			if (userId != null && quesId != null) {
				UserEntity user = userService.getUser(userId);
				QuestionEntity question = quesService.getQuestion(quesId);
				answer.setUser(user);
				answer.setQuestion(question);
				answer.setLikes(0L);
				answer = ansService.modifyAnswer(answer);
				return answer;
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public AnswerEntity updateAnswer(Long ansId, AnswerEntity answer)
			throws ForumException {
		if (answer != null && ansId != null && ansId.equals(answer.getAnsId())) {
			Long userId = answer.getAnsweredBy();
			Long quesId = answer.getQuesId();
			if (userId != null && quesId != null) {
				UserEntity user = userService.getUser(userId);
				QuestionEntity question = quesService.getQuestion(quesId);
				answer.setUser(user);
				answer.setQuestion(question);
				answer = ansService.modifyAnswer(answer);
				Long likes = ansService.getLikesByAnswer(ansId);
				answer.setLikes(likes);
				return answer;
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public void deleteAnswer(Long ansId) throws ForumException {
		if (ansId != null) {
			ansService.deleteAnsLikesByAnsId(ansId);
			ansService.deleteAnswer(ansId);
			return;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public AnswerLikeEntity likeAnswer(Long ansId, AnswerLikeEntity ansLike)
			throws ForumException {
		if (ansId != null && ansLike != null
				&& ansId.equals(ansLike.getAnsId())) {
			Long userId = ansLike.getUserId();
			Long quesId = ansLike.getQuesId();
			if (ansId != null && userId != null && quesId != null) {
				UserEntity user = userService.getUser(userId);
				QuestionEntity ques = quesService.getQuestion(quesId);
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
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}

	public void dislikeAnswer(Long ansId, AnswerLikeEntity ansLike)
			throws ForumException {
		if (ansId != null && ansLike != null
				&& ansId.equals(ansLike.getAnsId())) {
			Long userId = ansLike.getUserId();
			Long quesId = ansLike.getQuesId();
			if (ansId != null && userId != null && quesId != null) {
				UserEntity user = userService.getUser(userId);
				QuestionEntity ques = quesService.getQuestion(quesId);
				AnswerEntity ans = ansService.getAnswer(ansLike.getAnsId());
				AnswerLikeKey key = new AnswerLikeKey();
				key.setUser(user);
				key.setQuestion(ques);
				key.setAnswer(ans);
				ansLike.setAnsLikeKey(key);
				ansService.dislikeAnswer(ansLike);
				return;
			} else {
				throw new ForumException(
						ForumValidation.VALIDATION_FAILURE.getMessage());
			}
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}	
	
	public List<Integer> getAnswersLikedByUser(Long userId)
			throws ForumException {
		if (userId != null) {
			List<Integer> answerLikes = ansService
					.getAnswersLikedByUser(userId);
			return answerLikes;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
	public Long getUsersAnswersLikes(Long userId)
			throws ForumException {
		if (userId != null) {
			Long totalLikes = ansService
					.getUsersAnswersLikes(userId);
			return totalLikes;
		} else {
			throw new ForumException(
					ForumValidation.VALIDATION_FAILURE.getMessage());
		}
	}
	
}
