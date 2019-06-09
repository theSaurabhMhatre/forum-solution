package com.forum.mod.user.service;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.forum.app.core.DatabaseUtility;

import io.dropwizard.hibernate.AbstractDAO;

/**
 * This class uses the queries defined in UserEntity to query
 * the corresponding database table.
 * 
 * @author Saurabh Mhatre
 *
 */
@SuppressWarnings("unchecked")
public class UserRepo extends AbstractDAO<UserEntity> {
	private SessionFactory sessionFactory;

	public UserRepo() {
		super(DatabaseUtility.getSessionFactory());
		this.sessionFactory = DatabaseUtility.getSessionFactory();
	}

	public UserEntity getUser(Long userId) {
		UserEntity userEntity = super.get(userId);
		return userEntity;
	}

	public UserEntity modifyUser(UserEntity userEntity) {
		userEntity = super.persist(userEntity);
		return userEntity;
	}

	public Boolean deleteUser(Long userId) {
		int check = super.namedQuery("deleteUserQuery").setParameter("userId", userId).executeUpdate();
		if (check == 1) {
			return true;
		} else {
			return false;
		}
	}

	public UserEntity validateUser(UserEntity userEntity) {
		userEntity = super.uniqueResult(namedQuery("validateUserQuery")
				.setParameter("userName", userEntity.getUserName()).setParameter("userPswd", userEntity.getUserPswd()));
		return userEntity;
	}

	public UserEntity checkAvailability(String userName) {
		UserEntity userEntity = super.uniqueResult(
				namedQuery("checkAvailabilityQuery").setParameter("userName", userName));
		return userEntity;
	}

	public List<Long> getUserIds() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getUserIdsQuery");
		List<Long> userIds = query.getResultList();
		return userIds;
	}

	public List<Object> getAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getAllUsersQuery");
		List<Object> users = query.getResultList();
		return users;
	}

}
