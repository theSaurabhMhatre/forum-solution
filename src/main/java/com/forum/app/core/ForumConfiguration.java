package com.forum.app.core;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is the main configuration class which binds values from the config.yml
 * file to attributes to be used dynamically during runtime.
 * 
 * TODO: Check for hard coded properties if any.
 * 
 * @author Saurabh Mhatre
 */
public class ForumConfiguration extends Configuration {
	@NotNull
	private String appName;

	@NotNull
	private String appId;

	@NotNull
	private String appPswd;

	@NotNull
	private String uploadFileLocation;

	@NotNull
	private String contextPath;
	
	@NotNull
	private String allowedOrigins;

	@NotNull
	private String allowedHeaders;

	@NotNull
	private String allowedMethods;

	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory dataSourceFactory = new DataSourceFactory();

	public String getAppName() {
		return appName;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppPswd() {
		return appPswd;
	}

	public String getUploadFileLocation() {
		return uploadFileLocation;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getAllowedOrigins() {
		return allowedOrigins;
	}

	public String getAllowedHeaders() {
		return allowedHeaders;
	}

	public String getAllowedMethods() {
		return allowedMethods;
	}

	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

}
