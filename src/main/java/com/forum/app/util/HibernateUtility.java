package com.forum.app.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This is just a sample utility which is only being used to perform a health
 * check.
 * 
 * @author Saurabh Mhatre
 *
 */
public class HibernateUtility {
	private static EntityManagerFactory entityManagerFactory = null;

	/**
	 * This creates an instance of EntityManagerFactory using the configurations
	 * defined in persistence.xml file.
	 * 
	 * @return EntityManagerFactory entityManagerFactory
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
			return entityManagerFactory;
		} else {
			return entityManagerFactory;
		}
	}

	public static void closeEntityManagerfactory() {
		entityManagerFactory.close();
	}

}
