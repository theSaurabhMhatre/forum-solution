package com.forum.mod.question.service;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

@SuppressWarnings({ "rawtypes", "deprecation" })
public class QuestionRepo extends AbstractDAO<QuestionEntity> {

	public QuestionRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
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
	public List<QuestionEntity> getQuestions() {
		List<QuestionEntity> questions = super.namedQuery("getQuestionsQuery")
				.list();
		return questions;
	}

	public void deleteQuestion(Long quesId) {
		Query query = super.namedQuery("deleteQuestionQuery").setParameter(
				"quesId", quesId);
		query.executeUpdate();
	}

	public void deleteQuesByUserId(Long userId) {
		Query query = super.namedQuery("deleteQuesByUserIdQuery").setParameter(
				"userId", userId);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<QuestionEntity> getQuestionsByUser(Long userId) {
		Query query = super.namedQuery("getQuestionsByUserQuery").setParameter(
				"userId", userId);
		List<QuestionEntity> questions = query.list();
		return questions;
	}

}
