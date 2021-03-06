package com.forum.app.provider;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;

import com.forum.app.common.ResponseEntity;
import com.forum.app.constant.PersistenceError;

/**
 * This handles all the database related exceptions which are thrown during
 * runtime and returns an appropriate response message and status.
 * 
 * @author Saurabh Mhatre
 */
@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

	public Response toResponse(PersistenceException exception) {
		ResponseEntity responseEntity = resolveResponse(exception);
		Response response = Response.status(responseEntity.getResponseStatus().getStatusCode()).entity(responseEntity)
				.build();
		return response;
	}

	/**
	 * This identifies the type of exception thrown and returns an appropriate
	 * response status, message and object wrapped in ResponseEntity.
	 * 
	 * @param exception the thrown exception.
	 *
	 * @return A ResponseEntity instance which encapsulates the resolved response.
	 */
	public ResponseEntity resolveResponse(PersistenceException exception) {
		String message = null;
		ResponseEntity responseEntity = new ResponseEntity();
		responseEntity.setResponseStatus(Response.Status.INTERNAL_SERVER_ERROR);
		if (exception.getCause() instanceof ConstraintViolationException) {
			message = PersistenceError.CONSTRAINT_VIOLATION_EXCEPTION.getMessage();
		} else if (exception.getCause() instanceof DataException) {
			message = PersistenceError.DATA_EXCEPTION.getMessage();
		} else if (exception.getCause() instanceof SQLGrammarException) {
			message = PersistenceError.SQL_GRAMMAR_EXCEPTION.getMessage();
		} else if (exception.getCause() instanceof LockAcquisitionException) {
			message = PersistenceError.LOCK_ACQUISITION_EXCEPTION.getMessage();
		} else if (exception.getCause() instanceof JDBCConnectionException) {
			message = PersistenceError.JDBC_CONNECTION_EXCEPTION.getMessage();
		} else if (exception.getCause() instanceof GenericJDBCException) {
			message = PersistenceError.GENERIC_JDBC_EXCEPTION.getMessage();
		} else {
			message = exception.getMessage();
		}
		responseEntity.setResponseMessage(message);
		responseEntity.setResponseObject(message);
		return responseEntity;
	}

}
