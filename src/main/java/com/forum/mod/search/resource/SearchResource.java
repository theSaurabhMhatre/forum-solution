package com.forum.mod.search.resource;

import com.forum.app.common.ResponseEntity;
import com.forum.mod.search.factory.SearchResponseFactory;
import com.forum.mod.user.service.UserEntity;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class exposes a collection of search API end point(s) which can be used to
 * perform a variety of search operations on QuestionEntity and AnswerEntity.
 *
 * @author Saurabh Mhatre
 */
@PermitAll
@Api(value = "/search")
@Path(value = "/forum/search")
public class SearchResource {
    private SearchResponseFactory responseFactory;

    public SearchResource(SearchResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchResults(@ApiParam(hidden = true) @Auth UserEntity userEntityAuth,
                                     @QueryParam("type") String type,
                                     @QueryParam("category") String category,
                                     @QueryParam("keyword") String keyword) {
        ResponseEntity responseEntity = responseFactory.getSearchResults(type, category, keyword);
        Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
                .build();
        return response;
    }

}
