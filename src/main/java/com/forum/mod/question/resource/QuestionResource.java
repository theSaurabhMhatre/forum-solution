package com.forum.mod.question.resource;

import com.forum.app.common.ResponseEntity;
import com.forum.mod.question.factory.QuestionResponseFactory;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.user.service.UserEntity;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

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

/**
 * This class exposes a collection of question API end points which can be used
 * to perform a variety of operations related to QuestionEntity and
 * QuestionLikeEntity.
 *
 * @author Saurabh Mhatre
 */
@PermitAll
@Api(value = "/questions")
@Path(value = "/forum/questions")
public class QuestionResource {
    private QuestionResponseFactory responseFactory;

    public QuestionResource(QuestionResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @GET
    @UnitOfWork
    @Path(value = "/{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestion(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                @PathParam("questionId") Long questionId) {
        ResponseEntity responseEntity = responseFactory.getQuestion(questionId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestions(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth) {
        ResponseEntity responseEntity = responseFactory.getQuestionsWithMostLikedAns();
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addQuestion(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                QuestionEntity question) {
        ResponseEntity responseEntity = responseFactory.addQuestion(question);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @PUT
    @UnitOfWork
    @Path(value = "/{questionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateQuestion(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                   @PathParam("questionId") Long quesId,
                                   QuestionEntity question) {
        ResponseEntity responseEntity = responseFactory.updateQuestion(quesId, question);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @DELETE
    @UnitOfWork
    @Path(value = "/{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuestion(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                   @PathParam("questionId") Long quesId) {
        ResponseEntity responseEntity = responseFactory.deleteQuestion(quesId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @POST
    @UnitOfWork
    @Path(value = "/{questionId}/like")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeQuestion(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                 @PathParam("questionId") Long quesId,
                                 QuestionLikeEntity quesLike) {
        ResponseEntity responseEntity = responseFactory.likeQuestion(quesId, quesLike);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @DELETE
    @UnitOfWork
    @Path(value = "/{questionId}/dislike")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response dislikeQuestion(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                    @PathParam("questionId") Long quesId,
                                    QuestionLikeEntity quesLike) {
        ResponseEntity responseEntity = responseFactory.dislikeQuestion(quesId, quesLike);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @GET
    @UnitOfWork
    @Path(value = "/{userId}/likes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionsLikedByUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                            @PathParam("userId") Long userId) {
        ResponseEntity responseEntity = responseFactory.getQuestionsLikedByUser(userId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @GET
    @UnitOfWork
    @Path(value = "/{userId}/totallikes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersQuestionsLikes(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                           @PathParam("userId") Long userId) {
        ResponseEntity responseEntity = responseFactory.getUsersQuestionsLikes(userId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @GET
    @UnitOfWork
    @Path(value = "/{userId}/user/asked")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionsByUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                       @PathParam("userId") Long userId) {
        ResponseEntity responseEntity = responseFactory.getQuestionsByUser(userId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @GET
    @UnitOfWork
    @Path(value = "/{userId}/user/answered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionsAnsweredByUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                               @PathParam("userId") Long userId) {
        ResponseEntity responseEntity = responseFactory.getQuestionsAnsweredByUser(userId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

}
