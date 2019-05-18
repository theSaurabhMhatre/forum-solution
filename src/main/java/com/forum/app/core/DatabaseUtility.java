package com.forum.app.core;

import org.hibernate.SessionFactory;

import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.answer.service.AnswerLikeEntity;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.question.service.QuestionLikeEntity;
import com.forum.mod.user.service.UserEntity;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class DatabaseUtility {
	private static HibernateBundle<ForumConfiguration> hibernateBundle;
	
	static {
		hibernateBundle = new HibernateBundle<ForumConfiguration>(
				UserEntity.class, QuestionEntity.class, QuestionLikeEntity.class,
				AnswerEntity.class, AnswerLikeEntity.class) {
			public DataSourceFactory getDataSourceFactory(
					ForumConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		};	
	}

	public static HibernateBundle<ForumConfiguration> getHibernateBundle() {
		return hibernateBundle;
	}
	
	public static SessionFactory getSessionFactory() {
		return hibernateBundle.getSessionFactory();
	}

}