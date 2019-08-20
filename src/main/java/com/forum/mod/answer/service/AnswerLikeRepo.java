package com.forum.mod.answer.service;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;

import com.forum.app.core.DatabaseUtility;
import com.forum.app.key.AnswerLikeKey;

/**
 * This class uses the queries defined in AnswerLikeEntity to query
 * the corresponding database table.
 * 
 * @author Saurabh Mhatre
 */
@SuppressWarnings("unchecked")
public class AnswerLikeRepo {
	private SessionFactory sessionFactory;

	public AnswerLikeRepo() {
		this.sessionFactory = DatabaseUtility.getSessionFactory();
	}

	public AnswerLikeKey likeAnswer(AnswerLikeEntity ansLike) {
		AnswerLikeKey key = null;
		Session session = sessionFactory.getCurrentSession();
		key = (AnswerLikeKey) session.save(ansLike);
		return key;
	}

	public void dislikeAnswer(AnswerLikeEntity ansLike) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(ansLike);
		return;
	}

	public void deleteAnsLikesByAnsId(Long ansId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("deleteAnsLikesByAnsIdQuery").setParameter("ansId", ansId);
		query.executeUpdate();
	}

	public void deleteAnsLikesByQuesId(Long quesId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("deleteAnsLikesByQuesIdQuery").setParameter("quesId", quesId);
		query.executeUpdate();
	}

	public void deleteAnsLikesByUserId(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("deleteAnsLikesByUserIdQuery").setParameter("userId", userId);
		query.executeUpdate();
	}

	public List<Object> getLikesByAnswersByQuestion(Long quesId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getLikesByAnswersByQuestionQuery").setParameter("quesId", quesId);
		List<Object> ansLikes = query.getResultList();
		return ansLikes;
	}

	public Long getLikesByAnswer(Long ansId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getLikesByAnswerQuery").setParameter("ansId", ansId);
		Long likes = (Long) query.getResultList().get(0);
		return likes;
	}

	public List<Integer> getAnswersLikedByUser(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getAnswersLikedByUserQuery").setParameter("userId", userId);
		List<Integer> answerLikes = query.getResultList();
		return answerLikes;
	}

	public List<Object> getMostLikedAnsByQuestions() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getMostLikedAnsByQuestionsQuery");
		List<Object> result = query.getResultList();
		return result;
	}

	public Long getUsersAnswersLikes(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getUsersAnswersLikesQuery").setParameter("userId", userId)
				.addScalar("aLikes", LongType.INSTANCE);
		Long likes = (Long) query.getResultList().get(0);
		return likes;
	}

	public List<Object> getAllUsersAnswersLikes() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getAllUsersAnswersLikesQuery");
		List<Object> ansLikesByUsers = query.getResultList();
		return ansLikesByUsers;
	}

	public List<Object> getLikesByAnswers(String keyword, String category) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getLikesByAnswersQuery").setParameter("keyword", keyword)
				.setParameter("category", category).addScalar("likes", LongType.INSTANCE)
				.addScalar("ans_id", LongType.INSTANCE);
		List<Object> ansLikes = query.getResultList();
		return ansLikes;
	}

}
