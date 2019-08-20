package com.forum.app.constant;

/**
 * This provides descriptions for database related exceptions which may occur
 * when processing a request.
 * 
 * @author Saurabh Mhatre
 */
public enum PersistenceError {
	CONSTRAINT_VIOLATION_EXCEPTION("Integrity constraint violation"),

	DATA_EXCEPTION("Illegal operation, mismatched types or incorrect cardinality"),

	SQL_GRAMMAR_EXCEPTION("SQL sent to the database server was invalid"),

	LOCK_ACQUISITION_EXCEPTION("Problem acquiring lock on the database"),

	JDBC_CONNECTION_EXCEPTION("Problem communicating with the database"),

	GENERIC_JDBC_EXCEPTION("Generic, non-specific JDBCException");

	private String message;

	private PersistenceError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
