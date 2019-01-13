package com.forum.mod.question.resource;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forum.app.common.ResponseEntity;
import com.forum.mod.question.factory.QuestionResponseFactory;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;

@Path(value = "/forum/questions")
public class QuestionResource {
	private QuestionResponseFactory responseFactory;

	public QuestionResource(QuestionResponseFactory responseFactory) {
		this.responseFactory = responseFactory;
	}

	@GET
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuestions() {
		ResponseEntity responseEntity = responseFactory
				.getQuestionsWithMostLikedAns();
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}

	@POST
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addQuestion(QuestionEntity question) {
		ResponseEntity responseEntity = responseFactory.addQuestion(question);
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}

	@PUT
	@UnitOfWork
	@Path(value = "/{questionId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateQuestion(@PathParam("questionId") Long quesId,
			QuestionEntity question) {
		ResponseEntity responseEntity = responseFactory.updateQuestion(quesId,
				question);
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}

	@DELETE
	@UnitOfWork
	@Path(value = "/{questionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteQuestion(@PathParam("questionId") Long quesId) {
		ResponseEntity responseEntity = responseFactory.deleteQuestion(quesId);
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}

	@POST
	@UnitOfWork
	@Path(value = "/{questionId}/like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response likeQuestion(@PathParam("questionId") Long quesId,
			QuestionLikeEntity quesLike) {
		ResponseEntity responseEntity = responseFactory.likeQuestion(quesId,
				quesLike);
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}

	@GET
	@UnitOfWork
	@Path(value = "/{userId}/likes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuestionsLikedByUser(@PathParam("userId") Long userId) {
		ResponseEntity responseEntity = responseFactory
				.getQuestionsLikedByUser(userId);
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}

	@GET
	@UnitOfWork
	@Path(value = "/{userId}/totallikes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersQuestionsLikes(@PathParam("userId") Long userId) {
		ResponseEntity responseEntity = responseFactory
				.getUsersQuestionsLikes(userId);
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}
	
}
