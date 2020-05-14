package com.forum.app.key;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.user.service.UserEntity;

/**
 * This serves as the composite key for the table which stores the likes
 * corresponding to questions.
 * 
 * @author Saurabh Mhatre
 */
@Embeddable
public class QuestionLikeKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToOne
	@JoinColumn(name = "ques_id")
	private QuestionEntity question;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public QuestionEntity getQuestion() {
		return question;
	}

	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, question);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		QuestionLikeKey quesLike = (QuestionLikeKey) object;
		return getUser().getUserId().equals(quesLike.getUser().getUserId()) &&
				getQuestion().getQuesId().equals(quesLike.getQuestion().getQuesId());
	}

}
