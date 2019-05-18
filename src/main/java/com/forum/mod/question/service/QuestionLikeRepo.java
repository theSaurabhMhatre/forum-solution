package com.forum.mod.question.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;

import com.forum.app.core.DatabaseUtility;
import com.forum.app.key.QuestionLikeKey;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class QuestionLikeRepo {
	private SessionFactory sessionFactory;

	public QuestionLikeRepo() {
		this.sessionFactory = DatabaseUtility.getSessionFactory();
	}

	public QuestionLikeKey likeQuestion(QuestionLikeEntity quesLike) {
		QuestionLikeKey key = null;
		Session session = sessionFactory.getCurrentSession();
		key = (QuestionLikeKey) session.save(quesLike);
		return key;
	}

	public void dislikeQuestion(QuestionLikeEntity quesLike) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(quesLike);
		return;
	}

	public void deleteQuesLikesByQuesId(Long quesId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("deleteQuesLikesByQuesIdQuery")
				.setParameter("quesId", quesId);
		query.executeUpdate();
	}

	public void deleteQuesLikesByUserId(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("deleteQuesLikesByUserIdQuery")
				.setParameter("userId", userId);
		query.executeUpdate();
	}

	public List<Object> getLikesByQuestions(String keyword, String category) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getLikesByQuestionsQuery")
				.setParameter("keyword", keyword)
				.setParameter("category", category);
		List<Object> quesLikes = query.list();
		return quesLikes;
	}
	
	public List<Object> getLikesByQuestionsByUser(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getLikesByQuestionsByUserQuery")
				.setParameter("userId", userId)
				.addScalar("qlikes", LongType.INSTANCE)
				.addScalar("quesId", LongType.INSTANCE);
		List<Object> quesLikes = query.list();
		return quesLikes;
	}

	public List<Object> getLikesByQuestionsAnsweredByUser(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getLikesByQuestionsAnsweredByUserQuery")
				.setParameter("userId", userId)
				.addScalar("qlikes", LongType.INSTANCE)
				.addScalar("quesId", LongType.INSTANCE);
		List<Object> quesLikes = query.list();
		return quesLikes;
	}
	
	public Long getLikesByQuestion(Long quesId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getLikesByQuestionQuery")
				.setParameter("quesId", quesId);
		Long likes = (Long) query.list().get(0);
		return likes;
	}

	public List<Object> getLikesBySpecificQuestions(List<Long> quesIds) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getLikesBySpecificQuestionsQuery")
				.setParameterList("quesIds", quesIds);
		List<Object> quesLikes = query.list();
		return quesLikes;
	}
	
	public List<Integer> getQuestionsLikedByUser(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getQuestionsLikedByUserQuery")
				.setParameter("userId", userId);
		List<Integer> questionLikes = query.list();
		return questionLikes;
	}

	public Long getUsersQuestionsLikes(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getUsersQuestionsLikesQuery")
				.setParameter("userId", userId)
				.addScalar("qLikes", LongType.INSTANCE);
		Long likes = (Long) query.list().get(0);
		return likes;
	}
	
	public List<Object> getAllUsersQuestionsLikes() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedNativeQuery("getAllUsersQuestionsLikesQuery");
		List<Object> quesLikesByUsers = query.list();
		return quesLikesByUsers;
	}
	
}
