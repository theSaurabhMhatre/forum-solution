package com.forum.app;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.hibernate.SessionFactory;

import com.forum.mod.answer.factory.AnswerBusinessFactory;
import com.forum.mod.answer.factory.AnswerResponseFactory;
import com.forum.mod.answer.resource.AnswerResource;
import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;
import com.forum.mod.answer.service.AnswerLikeRepo;
import com.forum.mod.answer.service.AnswerRepo;
import com.forum.mod.answer.service.AnswerService;
import com.forum.mod.question.factory.QuestionBusinessFactory;
import com.forum.mod.question.factory.QuestionResponseFactory;
import com.forum.mod.question.resource.QuestionResource;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.question.service.QuestionLikeRepo;
import com.forum.mod.question.service.QuestionRepo;
import com.forum.mod.question.service.QuestionService;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.factory.UserResponseFactory;
import com.forum.mod.user.resource.UserResource;
import com.forum.mod.user.service.UserEntity;
import com.forum.mod.user.service.UserRepo;
import com.forum.mod.user.service.UserService;

public class ForumApplication extends Application<ForumConfiguration> {

	public static void main(String[] args) throws Exception {
		new ForumApplication().run(args);
	}

	private final HibernateBundle<ForumConfiguration> hibernateBundle = new HibernateBundle<ForumConfiguration>(
			UserEntity.class, QuestionEntity.class, QuestionLikeEntity.class,
			AnswerEntity.class, AnswerLikeEntity.class) {
		public DataSourceFactory getDataSourceFactory(
				ForumConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(Bootstrap<ForumConfiguration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(hibernateBundle);
	}

	@Override
	public void run(ForumConfiguration configuration, Environment environment)
			throws Exception {
		// Initializing session factory
		SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
		// Initializing services using session factories
		QuestionService quesService = new QuestionService(new QuestionRepo(
				sessionFactory), new QuestionLikeRepo(sessionFactory));
		AnswerService ansService = new AnswerService(new AnswerRepo(
				sessionFactory), new AnswerLikeRepo(sessionFactory));
		UserService userService = new UserService(new UserRepo(sessionFactory));
		// Initializing business factories using services
		UserBusinessFactory userBusinessFactory = new UserBusinessFactory(
				quesService, ansService, userService);
		QuestionBusinessFactory quesBusinessFactory = new QuestionBusinessFactory(
				quesService, ansService, userService);
		AnswerBusinessFactory ansBusinessFactory = new AnswerBusinessFactory(
				quesService, ansService, userService);

		// Registering UserResource
		environment.jersey().register(
				new UserResource(new UserResponseFactory(userBusinessFactory)));
		// Registering QuestionResource
		environment.jersey().register(
				new QuestionResource(new QuestionResponseFactory(
						quesBusinessFactory)));
		// Registering Answer Resource
		environment.jersey().register(
				new AnswerResource(
						new AnswerResponseFactory(ansBusinessFactory)));

		// Client for health check
		final Client client = new JerseyClientBuilder().build();
		environment.jersey().register(client);

		// Health check registration
		ForumHealthCheck healthCheck = new ForumHealthCheck(client);
		environment.healthChecks().register("ForumHealthCheck", healthCheck);

		// Enable CORS headers
		final FilterRegistration.Dynamic cors = environment.servlets()
				.addFilter("crossOriginRequests", CrossOriginFilter.class);
		// Configure CORS parameters
	    cors.setInitParameter("allowedOrigins", "*");
	    cors.setInitParameter("allowedHeaders", 
	    		"X-Requested-With, Content-Type, Accept, Origin");
	    cors.setInitParameter("allowedMethods", 
	    		"OPTIONS, GET, PUT, POST, DELETE, HEAD");
	    // Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class),
				true, "/*");
	}

}
