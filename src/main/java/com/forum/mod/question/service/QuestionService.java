package com.forum.mod.question.service;

import java.util.List;

import com.forum.app.constant.ForumError;
import com.forum.app.exception.DeleteException;
import com.forum.app.exception.FetchException;
import com.forum.app.exception.ModifyException;
import com.forum.app.key.QuestionLikeKey;

public class QuestionService {
	private QuestionRepo questionRepo;
	private QuestionLikeRepo likeRepo;

	public QuestionService(QuestionRepo questionRepo, QuestionLikeRepo likeRepo) {
		this.questionRepo = questionRepo;
		this.likeRepo = likeRepo;
	}

	public QuestionEntity getQuestion(Long quesId) throws FetchException {
		try {
			QuestionEntity question = questionRepo.getQuestion(quesId);
			if (question != null) {
				return question;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public List<QuestionEntity> getQuestions() throws FetchException {
		try {
			List<QuestionEntity> questions = questionRepo.getQuestions();
			if (questions != null) {
				return questions;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public QuestionEntity modifyQuestion(QuestionEntity question)
			throws ModifyException {
		try {
			question = questionRepo.modifyQuestion(question);
			if (question != null) {
				return question;
			} else {
				throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
		}
	}

	public void deleteQuestion(Long quesId) throws DeleteException {
		try {
			questionRepo.deleteQuestion(quesId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteQuesByUserId(Long userId) throws DeleteException {
		try {
			questionRepo.deleteQuesByUserId(userId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteQuesLikesByQuesId(Long quesId) throws DeleteException {
		try {
			likeRepo.deleteQuesLikesByQuesId(quesId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteQuesLikesByUserId(Long userId) throws DeleteException {
		try {
			likeRepo.deleteQuesLikesByUserId(userId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public List<Object> getLikesByQuestions() throws FetchException {
		try {
			List<Object> questionLikes = likeRepo.getLikesByQuestions();
			if (questionLikes != null) {
				return questionLikes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public Long getLikesByQuestion(Long quesId) throws FetchException {
		try {
			Long likes = likeRepo.getLikesByQuestion(quesId);
			if (likes != null) {
				return likes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public QuestionLikeKey likeQuestion(QuestionLikeEntity quesLike)
			throws ModifyException {
		try {
			QuestionLikeKey key = likeRepo.likeQuestion(quesLike);
			if (quesLike != null) {
				return key;
			} else {
				throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
		}
	}

	public List<Integer> getQuestionsLikedByUser(Long userId)
			throws FetchException {
		try {
			List<Integer> questionLikes = likeRepo
					.getQuestionsLikedByUser(userId);
			if (questionLikes != null) {
				return questionLikes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public Long getUsersQuestionsLikes(Long userId)
			throws FetchException {
		try {
			Long totalLikes = likeRepo.getUsersQuestionsLikes(userId);
			if (totalLikes != null) {
				return totalLikes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}
	
	public List<Object> getAllUsersQuestionsLikes()
			throws FetchException {
		try {
			List<Object> quesLikesByUsers = likeRepo.getAllUsersQuestionsLikes();
			if (quesLikesByUsers != null) {
				return quesLikesByUsers;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}
	
	public List<QuestionEntity> getQuestionsByUser(Long userId)
			throws FetchException {
		try {
			List<QuestionEntity> questions = questionRepo
					.getQuestionsByUser(userId);
			if (questions != null) {
				return questions;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

}