package com.forum.mod.answer.service;

import java.util.List;

import com.forum.app.constant.ForumError;
import com.forum.app.exception.DeleteException;
import com.forum.app.exception.FetchException;
import com.forum.app.exception.ModifyException;
import com.forum.app.key.AnswerLikeKey;

public class AnswerService {
	private AnswerRepo answerRepo;
	private AnswerLikeRepo likeRepo;

	public AnswerService(AnswerRepo answerRepo, AnswerLikeRepo likeRepo) {
		this.answerRepo = answerRepo;
		this.likeRepo = likeRepo;
	}

	public List<AnswerEntity> getAnswersByQuestion(Long quesId)
			throws FetchException {
		try {
			List<AnswerEntity> answers = answerRepo
					.getAnswersByQuestion(quesId);
			if (answers != null) {
				return answers;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public AnswerEntity getAnswer(Long ansId) throws FetchException {
		try {
			AnswerEntity answer = answerRepo.getAnswer(ansId);
			if (answer != null) {
				return answer;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public AnswerEntity modifyAnswer(AnswerEntity answer)
			throws ModifyException {
		try {
			answer = answerRepo.modifyAnswer(answer);
			if (answer != null) {
				return answer;
			} else {
				throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
		}
	}

	public void deleteAnswer(Long ansId) throws DeleteException {
		try {
			answerRepo.deleteAnswer(ansId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteAnsByQuesId(Long quesId) throws DeleteException {
		try {
			answerRepo.deleteAnsByQuesId(quesId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteAnsByUserId(Long userId) throws DeleteException {
		try {
			answerRepo.deleteAnsByUserId(userId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteAnsLikesByAnsId(Long ansId) throws DeleteException {
		try {
			likeRepo.deleteAnsLikesByAnsId(ansId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteAnsLikesByQuesId(Long quesId) throws DeleteException {
		try {
			likeRepo.deleteAnsLikesByQuesId(quesId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public void deleteAnsLikesByUserId(Long userId) throws DeleteException {
		try {
			likeRepo.deleteAnsLikesByUserId(userId);
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public List<Object> getLikesByAnswersByQuestion(Long quesId)
			throws FetchException {
		try {
			List<Object> answerLikes = likeRepo
					.getLikesByAnswersByQuestion(quesId);
			if (answerLikes != null) {
				return answerLikes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public Long getLikesByAnswer(Long ansId) throws FetchException {
		try {
			Long likes = likeRepo.getLikesByAnswer(ansId);
			if (likes != null) {
				return likes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public AnswerLikeKey likeAnswer(AnswerLikeEntity ansLike)
			throws ModifyException {
		try {
			AnswerLikeKey key = likeRepo.likeAnswer(ansLike);
			if (ansLike != null) {
				return key;
			} else {
				throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
		}
	}

	public void dislikeAnswer(AnswerLikeEntity ansLike)
			throws ModifyException {
		try {
			likeRepo.dislikeAnswer(ansLike);
		} catch (Exception ex) {
			throw new ModifyException(ForumError.DELETE_ERROR.getMessage());
		}
	}	
	
	public List<Integer> getAnswersLikedByUser(Long userId)
			throws FetchException {
		try {
			List<Integer> answerLikes = likeRepo.getAnswersLikedByUser(userId);
			if (answerLikes != null) {
				return answerLikes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public Long getUsersAnswersLikes(Long userId)
			throws FetchException {
		try {
			Long totalLikes = likeRepo.getUsersAnswersLikes(userId);
			if (totalLikes != null) {
				return totalLikes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}
	
	public List<Object> getAllUsersAnswersLikes()
			throws FetchException {
		try {
			List<Object> ansLikesByUsers = likeRepo.getAllUsersAnswersLikes();
			if (ansLikesByUsers != null) {
				return ansLikesByUsers;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}
	
	public List<Object> getMostLikedAnsByQuestions() throws FetchException {
		try {
			List<Object> result = likeRepo.getMostLikedAnsByQuestions();
			if (result != null) {
				return result;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public AnswerEntity getAnsByQuesId(Long quesId) throws FetchException {
		try {
			AnswerEntity answer = answerRepo.getAnsByQuesId(quesId);
			if (answer != null) {
				return answer;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public List<AnswerEntity> getAnswersByUser(Long userId)
			throws FetchException {
		try {
			List<AnswerEntity> answers = answerRepo.getAnswersByUser(userId);
			if (answers != null) {
				return answers;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public List<AnswerEntity> getAnswers(String keyword, String category) 
			throws FetchException {
		try {
			List<AnswerEntity> answers = answerRepo.getAnswers(keyword, category);
			if (answers != null) {
				return answers;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}	
	
	public List<Object> getLikesByAnswers(String keyword, String category)
			throws FetchException {
		try {
			List<Object> answerLikes = likeRepo.getLikesByAnswers(keyword, category);
			if (answerLikes != null) {
				return answerLikes;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}
	
}
