package com.forum.app.core;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.client.Client;

import com.codahale.metrics.health.HealthCheck;
import com.forum.app.util.HibernateUtility;

/**
 * This performs a basic health check to prevent the health check warning from
 * being displayed during application start.
 * 
 * TODO: Add more health checks.
 * 
 * @author Saurabh Mhatre
 */
public class ForumHealthCheck extends HealthCheck {
	@SuppressWarnings("unused")
	private Client client;

	public ForumHealthCheck(Client client) {
		super();
		this.client = client;
	}

	/**
	 * Performs a simple health check by trying to create an EntityManagerFactory instance.
	 * 
	 * @return A Result instance indicating the result (Healthy/Unhealthy) of the health check.
	 */
	@Override
	protected Result check() throws Exception {
		EntityManagerFactory entityManagerFactory = HibernateUtility.getEntityManagerFactory();
		if (entityManagerFactory != null) {
			HibernateUtility.closeEntityManagerfactory();
			return Result.healthy("Healthy");
		}
		return Result.unhealthy("Unhealthy");
	}

}
