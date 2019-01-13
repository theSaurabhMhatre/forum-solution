package com.forum.mod.question.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.forum.mod.user.service.UserEntity;

@Entity
@Table(name = "table_question")
@NamedQueries({
		@NamedQuery(name = "getQuestionsQuery", query = "from QuestionEntity ques "
				+ "order by ques.quesId"),
		@NamedQuery(name = "deleteQuestionQuery", query = "delete from QuestionEntity ques "
				+ "where ques.quesId = :quesId"),
		@NamedQuery(name = "deleteQuesByUserIdQuery", query = "delete from QuestionEntity ques "
				+ "where user.userId = :userId"),
		@NamedQuery(name = "getQuestionsByUserQuery", query = "from QuestionEntity ques "
				+ "where ques.user.userId = :userId") })
public class QuestionEntity {
	@Id
	@Column(name = "ques_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "QuestionSequence", sequenceName = "question_sequence")
	private Long quesId;

	@Column(name = "ques_str")
	private String ques;

	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "asked_by")
	private UserEntity user;

	@Transient
	@JsonProperty(value = "askedBy")
	private Long askedBy;

	@Column(name = "ques_category")
	private String category;

	@Transient
	@JsonProperty(value = "likes")
	private Long likes;

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getQuesId() {
		return quesId;
	}

	public void setQuesId(Long quesId) {
		this.quesId = quesId;
	}

	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getAskedBy() {
		return askedBy;
	}

	public void setAskedBy(Long askedBy) {
		this.askedBy = askedBy;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
