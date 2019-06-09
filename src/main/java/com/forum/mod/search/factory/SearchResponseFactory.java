package com.forum.mod.search.factory;

import javax.ws.rs.core.Response;

import com.forum.app.common.ResponseEntity;
import com.forum.app.constant.ForumSuccess;
import com.forum.app.exception.ForumException;
import com.forum.mod.search.service.SearchEntity;

/**
 * This class wraps the response returned by the SearchBusinessFactory
 * into a ResponseEntity object and sets the appropriate response message
 * and the response status code.
 * 
 * @author Saurabh Mhatre
 *
 */
public class SearchResponseFactory {
	private SearchBusinessFactory businessFactory;

	public SearchResponseFactory(SearchBusinessFactory businessFactory) {
		this.businessFactory = businessFactory;
	}

	public ResponseEntity getSearchResults(String type, String category, String keyword) {
		ResponseEntity response = new ResponseEntity();
		try {
			SearchEntity searchEntity = businessFactory.getSearchResults(type, category, keyword);
			response.setResponseStatus(Response.Status.OK);
			response.setResponseMessage(ForumSuccess.FETCH_SUCCESS.getMessage());
			response.setResponseObject(searchEntity);
			return response;
		} catch (ForumException ex) {
			response.setResponseStatus(Response.Status.BAD_REQUEST);
			response.setResponseMessage(ex.getMessage());
			response.setResponseObject(ex.getMessage());
			return response;
		}
	}
}
