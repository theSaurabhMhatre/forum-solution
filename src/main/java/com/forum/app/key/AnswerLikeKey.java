package com.forum.app.key;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.forum.mod.answer.service.AnswerEntity;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.user.service.UserEntity;

/**
 * This serves as the composite key for the table which stores the likes
 * corresponding to answers.
 * 
 * @author Saurabh Mhatre
 *
 */
@Embeddable
public class AnswerLikeKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToOne
	@JoinColumn(name = "ques_id")
	private QuestionEntity question;

	@OneToOne
	@JoinColumn(name = "ans_id")
	private AnswerEntity answer;

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

	public AnswerEntity getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerEntity answer) {
		this.answer = answer;
	}

}
