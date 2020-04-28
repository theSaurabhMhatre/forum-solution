package com.forum.mod.user.resource;

import com.forum.app.common.ResponseEntity;
import com.forum.mod.user.factory.UserResponseFactory;
import com.forum.mod.user.service.UserEntity;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
import java.io.InputStream;

/**
 * This class exposes a collection of user API end points which can be used to
 * perform a variety of operations related to UserEntity.
 *
 * @author Saurabh Mhatre
 */
@PermitAll
@Api(value = "/users")
@Path(value = "/forum/users")
public class UserResource {
    private UserResponseFactory responseFactory;

    public UserResource(UserResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @GET
    @UnitOfWork
    @Path(value = "/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                            @PathParam("userId") Long userId) {
        ResponseEntity responseEntity = responseFactory.getUser(userId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                            UserEntity userEntity) {
        ResponseEntity responseEntity = responseFactory.addUser(userEntity);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @PUT
    @UnitOfWork
    @Path(value = "/{userId}/{attribute}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                               @PathParam("userId") Long userId,
                               @PathParam("attribute") String attribute,
                               UserEntity userEntity) {
        ResponseEntity responseEntity = responseFactory.updateUser(userId, userEntity, attribute);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @DELETE
    @UnitOfWork
    @Path(value = "/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                               @PathParam("userId") Long userId) {
        ResponseEntity responseEntity = responseFactory.deleteUser(userId);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @POST
    @UnitOfWork
    @Path(value = "/{userName}/validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUser(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                 @PathParam("userName") String userName,
                                 UserEntity userEntity) {
        ResponseEntity responseEntity = responseFactory.validateUser(userName, userEntity);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @POST
    @UnitOfWork
    @Path(value = "/{userName}/avatar/{mode}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadUserAvatar(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                     @PathParam("userName") String userName,
                                     @PathParam("mode") String mode,
                                     @FormDataParam("userAvatar") InputStream inputStream,
                                     @FormDataParam("userAvatar") FormDataContentDisposition fileDetails) {
        ResponseEntity responseEntity = responseFactory.uploadUserAvatar(userName, inputStream, fileDetails, mode);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @GET
    @UnitOfWork
    @Path(value = "/{userName}/available")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkAvailability(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                      @PathParam("userName") String userName) {
        ResponseEntity responseEntity = responseFactory.checkAvailability(userName);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

    @GET
    @UnitOfWork
    @Path(value = "/ranking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersRanking(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth) {
        ResponseEntity responseEntity = responseFactory.getUserRankings();
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

}
