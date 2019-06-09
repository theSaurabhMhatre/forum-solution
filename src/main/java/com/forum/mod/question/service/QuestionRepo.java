package com.forum.mod.question.service;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import javax.persistence.Query;

import com.forum.app.core.DatabaseUtility;

/**
 * This class uses the queries defined in QuestionEntity to query
 * the corresponding database table.
 * 
 * @author Saurabh Mhatre
 *
 */
public class QuestionRepo extends AbstractDAO<QuestionEntity> {

	public QuestionRepo() {
		super(DatabaseUtility.getSessionFactory());
	}

	public QuestionEntity getQuestion(Long quesId) {
		QuestionEntity question = super.get(quesId);
		return question;
	}

	public QuestionEntity modifyQuestion(QuestionEntity question) {
		question = super.persist(question);
		return question;
	}

	@SuppressWarnings("unchecked")
	public List<QuestionEntity> getQuestions(String keyword, String category) {
		List<QuestionEntity> questions = super.namedQuery("getQuestionsQuery").setParameter("keyword", keyword)
				.setParameter("category", category).list();
		return questions;
	}

	public void deleteQuestion(Long quesId) {
		Query query = super.namedQuery("deleteQuestionQuery").setParameter("quesId", quesId);
		query.executeUpdate();
	}

	public void deleteQuesByUserId(Long userId) {
		Query query = super.namedQuery("deleteQuesByUserIdQuery").setParameter("userId", userId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<QuestionEntity> getQuestionsByUser(Long userId) {
		Query query = super.namedQuery("getQuestionsByUserQuery").setParameter("userId", userId);
		List<QuestionEntity> questions = query.getResultList();
		return questions;
	}

}
