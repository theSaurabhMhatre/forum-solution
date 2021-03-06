package com.forum.mod.question.factory;

import java.util.List;

import javax.ws.rs.core.Response;

import com.forum.app.common.ResponseEntity;
import com.forum.app.constant.ForumSuccess;
import com.forum.app.exception.ForumException;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;

/**
 * This class wraps the response returned by the QuestionBusinessFactory
 * into a ResponseEntity object and sets the appropriate response message
 * and the response status code.
 * 
 * @author Saurabh Mhatre
 */
public class QuestionResponseFactory {
	private QuestionBusinessFactory businessFactory;

	public QuestionResponseFactory(QuestionBusinessFactory businessFactory) {
		this.businessFactory = businessFactory;
	}

	public ResponseEntity getQuestion(Long quesId) {
		ResponseEntity response = new ResponseEntity();
		try {
			QuestionEntity question = businessFactory.getQuestion(quesId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(question);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity getQuestionsWithMostLikedAns() {
		ResponseEntity response = new ResponseEntity();
		try {
			List<Object> quesWithAns = businessFactory.getQuestionsWithMostLikedAns();
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(quesWithAns);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity addQuestion(QuestionEntity question) {
		ResponseEntity response = new ResponseEntity();
		try {
			question = businessFactory.addQuestion(question);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS.getMessage());
			response.setResponseObject(question);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity updateQuestion(Long quesId, QuestionEntity question) {
		ResponseEntity response = new ResponseEntity();
		try {
			question = businessFactory.updateQuestion(quesId, question);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS.getMessage());
			response.setResponseObject(question);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity deleteQuestion(Long quesId) {
		ResponseEntity response = new ResponseEntity();
		try {
			businessFactory.deleteQuestion(quesId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.DELETE_SUCCESS.getMessage());
			response.setResponseObject(ForumSuccess.DELETE_SUCCESS.getMessage());
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity likeQuestion(Long quesId, QuestionLikeEntity quesLike) {
		ResponseEntity response = new ResponseEntity();
		try {
			quesLike = businessFactory.likeQuestion(quesId, quesLike);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS.getMessage());
			response.setResponseObject(quesLike);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity dislikeQuestion(Long quesId, QuestionLikeEntity quesLike) {
		ResponseEntity response = new ResponseEntity();
		try {
			businessFactory.dislikeQuestion(quesId, quesLike);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.DELETE_SUCCESS.getMessage());
			response.setResponseObject(ForumSuccess.DELETE_SUCCESS.getMessage());
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity getQuestionsLikedByUser(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			List<Integer> questionLikes = businessFactory.getQuestionsLikedByUser(userId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(questionLikes);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity getUsersQuestionsLikes(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			Long answerLikes = businessFactory.getUsersQuestionsLikes(userId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(answerLikes);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity getQuestionsByUser(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			Boolean withLikes = true;
			List<QuestionEntity> questions = businessFactory.getQuestionsByUser(userId, withLikes);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(questions);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity getQuestionsAnsweredByUser(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			List<Object> questions = businessFactory.getQuestionsAnsweredByUser(userId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(questions);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

}
