package com.forum.app.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtility {
	private static EntityManagerFactory entityManagerFactory = null;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("persistence");
			return entityManagerFactory;
		} else {
			return entityManagerFactory;
		}
	}
	
	public static void closeEntityManagerfactory(){
		entityManagerFactory.close();
	}

}
