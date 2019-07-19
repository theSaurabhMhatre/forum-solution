package com.forum.app.core;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.client.JerseyClientBuilder;

import com.forum.app.provider.PersistenceExceptionMapper;
import com.forum.app.util.FileUtility;
import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.factory.AnswerResponseFactory;
import com.forum.mod.answer.resource.AnswerResource;
import com.forum.mod.question.factory.QuestionBusinessFactory;
import com.forum.mod.question.factory.QuestionResponseFactory;
import com.forum.mod.question.resource.QuestionResource;
import com.forum.mod.search.factory.SearchBusinessFactory;
import com.forum.mod.search.factory.SearchResponseFactory;
import com.forum.mod.search.resource.SearchResource;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.factory.UserResponseFactory;
import com.forum.mod.user.resource.UserResource;

import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * This is the entry point of the application. It initializes all the resources,
 * health checks, exception mappers and sets up the CORS configuration. It also
 * sets up the dependencies required by the resources.
 * 
 * TODO: Integrate swagger.
 * 
 * @author Saurabh Mhatre
 *
 */
public class ForumApplication extends Application<ForumConfiguration> {

	public static void main(String[] args) throws Exception {
		new ForumApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<ForumConfiguration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(DatabaseUtility.getHibernateBundle());
		bootstrap.addBundle(new MultiPartBundle());
	}

	@Override
	public void run(ForumConfiguration configuration, Environment environment) throws Exception {
		// Initializing business factories
		UserBusinessFactory userBusinessFactory = new UserBusinessFactory();
		QuestionBusinessFactory quesBusinessFactory = new QuestionBusinessFactory();
		AnswerBusinessFactory ansBusinessFactory = new AnswerBusinessFactory();
		SearchBusinessFactory searchBusinessFactory = new SearchBusinessFactory();

		// Setting business factory dependencies
		userBusinessFactory.setBusinessFactories(ansBusinessFactory, quesBusinessFactory);
		quesBusinessFactory.setBusinessFactories(userBusinessFactory, ansBusinessFactory);
		ansBusinessFactory.setBusinessFactories(userBusinessFactory, quesBusinessFactory);
		searchBusinessFactory.setBusinessFactories(ansBusinessFactory, quesBusinessFactory);

		// Initializing response factories
		UserResponseFactory userResponseFactory = new UserResponseFactory(userBusinessFactory);
		QuestionResponseFactory quesResponseFactory = new QuestionResponseFactory(quesBusinessFactory);
		AnswerResponseFactory ansResponseFactory = new AnswerResponseFactory(ansBusinessFactory);
		SearchResponseFactory searchResponseFactory = new SearchResponseFactory(searchBusinessFactory);
		
		// Setting upload file location for uploading user avatars
		FileUtility.uploadLocation = configuration.getUploadFileLocation();
		// Setting context path which will be stored in the DB
		FileUtility.contextPath = configuration.getContextPath();
		
		// Registering UserResource
		environment.jersey().register(new UserResource(userResponseFactory));
		// Registering QuestionResource
		environment.jersey().register(new QuestionResource(quesResponseFactory));
		// Registering Answer Resource
		environment.jersey().register(new AnswerResource(ansResponseFactory));
		// Registering Search Resource
		environment.jersey().register(new SearchResource(searchResponseFactory));

		// Client for health check
		final Client client = new JerseyClientBuilder().build();
		environment.jersey().register(client);

		// Health check registration
		ForumHealthCheck healthCheck = new ForumHealthCheck(client);
		environment.healthChecks().register("ForumHealthCheck", healthCheck);

		// Registering Persistence Exception Mapper
		PersistenceExceptionMapper exceptionMapper = new PersistenceExceptionMapper();
		environment.jersey().register(exceptionMapper);

		// Enable CORS headers
		final FilterRegistration.Dynamic cors = environment.servlets().addFilter("crossOriginRequests",
				CrossOriginFilter.class);
		// Configure CORS parameters
		cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, configuration.getAllowedOrigins());
		cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, configuration.getAllowedHeaders());
		cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, configuration.getAllowedMethods());
		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}

}
