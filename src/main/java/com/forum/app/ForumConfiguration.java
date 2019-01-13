package com.forum.app;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForumConfiguration extends Configuration {
	private String appName;

	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory dataSourceFactory = new DataSourceFactory();

	public String getAppName() {
		return appName;
	}

	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

}
