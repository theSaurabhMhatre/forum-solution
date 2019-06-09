package com.forum.mod.answer.factory;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;

import com.forum.app.common.ResponseEntity;
import com.forum.app.constant.ForumSuccess;
import com.forum.app.exception.ForumException;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;

/**
 * This class wraps the response returned by the AnswerBusinessFactory
 * into a ResponseEntity object and sets the appropriate response message
 * and the response status code.
 * 
 * @author Saurabh Mhatre
 *
 */
public class AnswerResponseFactory {
	private AnswerBusinessFactory businessFactory;

	public AnswerResponseFactory(AnswerBusinessFactory businessFactory) {
		this.businessFactory = businessFactory;
	}

	public ResponseEntity getAnswersByQuestion(Long quesId) {
		ResponseEntity response = new ResponseEntity();
		try {
			Set<AnswerEntity> answers = businessFactory.getAnswersByQuestion(quesId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(answers);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity addAnswer(AnswerEntity answer) {
		ResponseEntity response = new ResponseEntity();
		try {
			answer = businessFactory.addAnswer(answer);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS.getMessage());
			response.setResponseObject(answer);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity updateAnswer(Long ansId, AnswerEntity answer) {
		ResponseEntity response = new ResponseEntity();
		try {
			answer = businessFactory.updateAnswer(ansId, answer);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS.getMessage());
			response.setResponseObject(answer);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity deleteAnswer(Long ansId) {
		ResponseEntity response = new ResponseEntity();
		try {
			businessFactory.deleteAnswer(ansId);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.DELETE_SUCCESS.getMessage());
			response.setResponseObject(ForumSuccess.DELETE_SUCCESS.getMessage());
			return response;
		} catch (Exception ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity likeAnswer(Long ansId, AnswerLikeEntity ansLike) {
		ResponseEntity response = new ResponseEntity();
		try {
			ansLike = businessFactory.likeAnswer(ansId, ansLike);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.MODIFY_SUCCESS.getMessage());
			response.setResponseObject(ansLike);
			return response;
		} catch (Exception ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity dislikeAnswer(Long ansId, AnswerLikeEntity ansLike) {
		ResponseEntity response = new ResponseEntity();
		try {
			businessFactory.dislikeAnswer(ansId, ansLike);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.DELETE_SUCCESS.getMessage());
			response.setResponseObject(ForumSuccess.DELETE_SUCCESS.getMessage());
			return response;
		} catch (Exception ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}

	public ResponseEntity getAnswersLikedByUser(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			List<Integer> answerLikes = businessFactory.getAnswersLikedByUser(userId);
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

	public ResponseEntity getUsersAnswersLikes(Long userId) {
		ResponseEntity response = new ResponseEntity();
		try {
			Long answerLikes = businessFactory.getUsersAnswersLikes(userId);
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

}
