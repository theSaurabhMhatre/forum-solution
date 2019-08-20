package com.forum.mod.answer.resource;

import com.forum.mod.user.service.UserEntity;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.PermitAll;
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
import com.forum.mod.answer.factory.AnswerResponseFactory;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;

/**
 * This class exposes a collection of answer API end points which can be used to
 * perform a variety of operations related to AnswerEntity and AnswerLikeEntity.
 * 
 * @author Saurabh Mhatre
 */
@PermitAll
@Path(value = "/forum/answers")
public class AnswerResource {
	private AnswerResponseFactory responseFactory;

	public AnswerResource(AnswerResponseFactory responseFactory) {
		this.responseFactory = responseFactory;
	}

	@GET
	@UnitOfWork
	@Path(value = "/{quesId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswersByQuestion(@Auth UserEntity userEntityAuth, @PathParam("quesId") Long quesId) {
		ResponseEntity responseEntity = responseFactory.getAnswersByQuestion(quesId);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@POST
	@UnitOfWork
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAnswer(@Auth UserEntity userEntityAuth, AnswerEntity answer) {
		ResponseEntity responseEntity = responseFactory.addAnswer(answer);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@PUT
	@UnitOfWork
	@Path(value = "/{ansId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAnswer(@Auth UserEntity userEntityAuth, @PathParam("ansId") Long ansId, AnswerEntity answer) {
		ResponseEntity responseEntity = responseFactory.updateAnswer(ansId, answer);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@DELETE
	@UnitOfWork
	@Path(value = "/{ansId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAnswer(@Auth UserEntity userEntityAuth, @PathParam("ansId") Long ansId) {
		ResponseEntity responseEntity = responseFactory.deleteAnswer(ansId);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@POST
	@UnitOfWork
	@Path(value = "/{ansId}/like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response likeAnswer(@Auth UserEntity userEntityAuth, @PathParam("ansId") Long ansId, AnswerLikeEntity ansLike) {
		ResponseEntity responseEntity = responseFactory.likeAnswer(ansId, ansLike);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@DELETE
	@UnitOfWork
	@Path(value = "/{ansId}/dislike")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dislikeAnswer(@Auth UserEntity userEntityAuth, @PathParam("ansId") Long ansId,
								  AnswerLikeEntity ansLike) {
		ResponseEntity responseEntity = responseFactory.dislikeAnswer(ansId, ansLike);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@GET
	@UnitOfWork
	@Path(value = "/{userId}/likes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswersLikedByUser(@Auth UserEntity userEntityAuth, @PathParam("userId") Long userId) {
		ResponseEntity responseEntity = responseFactory.getAnswersLikedByUser(userId);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@GET
	@UnitOfWork
	@Path(value = "/{userId}/totallikes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsersAnswersLikes(@Auth UserEntity userEntityAuth, @PathParam("userId") Long userId) {
		ResponseEntity responseEntity = responseFactory.getUsersAnswersLikes(userId);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	@GET
	@UnitOfWork
	@Path(value = "/{ansId}/question/{quesId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAnswerByQuesAnsPair(@Auth UserEntity userEntityAuth, @PathParam("ansId") Long ansId,
										   @PathParam("quesId") Long quesId) {
		ResponseEntity responseEntity = responseFactory.getAnswerByQuesAnsPair(ansId, quesId);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

}
