package com.forum.mod.search.resource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forum.app.common.ResponseEntity;
import com.forum.mod.search.factory.SearchResponseFactory;

import com.forum.mod.user.service.UserEntity;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * This class exposes a collection of search API end point(s) which can be used to
 * perform a variety of search operations on QuestionEntity and AnswerEntity.
 *
 * @author Saurabh Mhatre
 */
@PermitAll
@Path(value = "/forum/search")
public class SearchResource {
    private SearchResponseFactory responseFactory;

    public SearchResource(SearchResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchResults(@Auth UserEntity userEntityAuth, @QueryParam("type") String type,
                                     @QueryParam("category") String category, @QueryParam("keyword") String keyword) {
        ResponseEntity responseEntity = responseFactory.getSearchResults(type, category, keyword);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

}
