package com.forum.app.core;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.client.Client;

import com.codahale.metrics.health.HealthCheck;
import com.forum.app.util.HibernateUtility;

public class ForumHealthCheck extends HealthCheck {
	@SuppressWarnings("unused")
	private Client client;
	
	public ForumHealthCheck(Client client) {
		super();
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		EntityManagerFactory entityManagerFactory = HibernateUtility
				.getEntityManagerFactory();
		if (entityManagerFactory != null) {
			HibernateUtility.closeEntityManagerfactory();
			return Result.healthy("Healthy");
		}
		return Result.unhealthy("Unhealthy");
	}

}
