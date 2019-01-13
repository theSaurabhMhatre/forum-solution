package com.forum.mod.question.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;

import com.forum.app.key.QuestionLikeKey;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class QuestionLikeRepo {
	private SessionFactory sessionFactory;

	public QuestionLikeRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public QuestionLikeKey likeQuestion(QuestionLikeEntity quesLike) {
		QuestionLikeKey key = null;
		Session session = sessionFactory.getCurrentSession();
		key = (QuestionLikeKey) session.save(quesLike);
		return key;
	}

	public void dislikeQuestion(QuestionLikeEntity quesLike) {
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

	public List<Object> getLikesByQuestions() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.getNamedQuery("getLikesByQuestionsQuery");
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
				.setParameter("userId", userId).addScalar("qLikes", LongType.INSTANCE);
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