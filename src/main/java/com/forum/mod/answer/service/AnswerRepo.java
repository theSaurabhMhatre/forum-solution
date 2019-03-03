package com.forum.mod.answer.service;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

@SuppressWarnings({ "rawtypes", "deprecation" })
public class AnswerRepo extends AbstractDAO<AnswerEntity> {

	public AnswerRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public List<AnswerEntity> getAnswersByQuestion(Long quesId) {
		List<AnswerEntity> answers = super.namedQuery("getAnswersByQuestionQuery")
				.setParameter("quesId", quesId).list();
		return answers;
	}

	public AnswerEntity getAnswer(Long ansId) {
		AnswerEntity answer = super.get(ansId);
		return answer;
	}

	public AnswerEntity modifyAnswer(AnswerEntity answer) {
		answer = super.persist(answer);
		return answer;
	}

	public void deleteAnswer(Long ansId) {
		Query query = super.namedQuery("deleteAnswerQuery")
				.setParameter("ansId", ansId);
		query.executeUpdate();
	}

	public void deleteAnsByQuesId(Long quesId) {
		Query query = super.namedQuery("deleteAnsByQuesIdQuery")
				.setParameter("quesId", quesId);
		query.executeUpdate();
	}

	public void deleteAnsByUserId(Long userId) {
		Query query = super.namedQuery("deleteAnsByUserIdQuery")
				.setParameter("userId", userId);
		query.executeUpdate();
	}

	public AnswerEntity getAnsByQuesId(Long quesId) {
		Query query = super.namedQuery("getAnsByQuesIdQuery")
				.setParameter("quesId", quesId).setMaxResults(1);
		AnswerEntity answer = (AnswerEntity) query.getSingleResult();
		return answer;
	}

	@SuppressWarnings("unchecked")
	public List<AnswerEntity> getAnswersByUser(Long userId) {
		Query query = super.namedQuery("getAnswersByUserQuery")
				.setParameter("userId", userId);
		List<AnswerEntity> answers = query.list();
		return answers;
	}
	
	@SuppressWarnings("unchecked")
	public List<AnswerEntity> getAnswers(String keyword, String category) {
		List<AnswerEntity> answers = super.namedQuery("getAnswersQuery")
				.setParameter("keyword", keyword)
				.setParameter("category", category)
				.list();
		return answers;
	}

}
