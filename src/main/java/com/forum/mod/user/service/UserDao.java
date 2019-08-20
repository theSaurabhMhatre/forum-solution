package com.forum.mod.user.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.forum.app.util.HibernateUtility;

/**
 * This class performs database CRUD operates for UserEntity.
 * 
 * @author Saurabh Mhatre
 *
 * @deprecated Please refer {@link UserRepo}.
 */
@Deprecated
public class UserDao {

	public UserEntity getUser(Long userId) {
		UserEntity userEntity = null;
		EntityManagerFactory entityManagerFactory = HibernateUtility.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		userEntity = entityManager.find(UserEntity.class, userId);
		entityManager.getTransaction().commit();
		return userEntity;
	}

	public UserEntity modifyUser(UserEntity userEntity) {
		EntityManagerFactory entityManagerFactory = HibernateUtility.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(userEntity);
		entityManager.getTransaction().commit();
		return userEntity;
	}

	public void deleteUser(UserEntity userEntity) {
		EntityManagerFactory entityManagerFactory = HibernateUtility.getEntityManagerFactory();
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager);
		entityManager.getTransaction().commit();
		return;
	}

}
