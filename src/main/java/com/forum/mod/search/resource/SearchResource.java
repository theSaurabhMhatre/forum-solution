package com.forum.mod.search.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forum.app.common.ResponseEntity;
import com.forum.mod.search.factory.SearchResponseFactory;

import io.dropwizard.hibernate.UnitOfWork;

@Path(value = "/forum/search")
public class SearchResource {
	private SearchResponseFactory responseFactory;
	
	public SearchResource(SearchResponseFactory responseFactory) {
		this.responseFactory = responseFactory;
	}
	
	@GET
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchResults(@QueryParam("type") String type, 
			@QueryParam("category") String category, @QueryParam("keyword") String keyword) {
		ResponseEntity responseEntity = responseFactory
				.getSearchResults(type, category, keyword);
		Response response = Response
				.status(responseEntity.getResponseStatus().getStatusCode())
				.entity(responseEntity).build();
		return response;
	}
	
}
