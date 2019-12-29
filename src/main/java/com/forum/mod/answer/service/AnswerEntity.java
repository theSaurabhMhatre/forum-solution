package com.forum.mod.answer.service;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.forum.mod.question.service.QuestionEntity;
import com.forum.mod.user.service.UserEntity;

/**
 * This class represents the AnswerEntity POJO and the mapping of the same to
 * the corresponding database table. It also contains queries used by AnswerRepo
 * to query the table corresponding to AnswerEntity.
 * 
 * @author Saurabh Mhatre
 */
@Entity
@Table(name = "table_answer")
@NamedQueries({
		@NamedQuery(name = "getAnswersQuery", query = "from AnswerEntity ans " 
				+ "where ans.question.quesId in ( "
				+ "select ques.quesId "
				+ "from QuestionEntity ques "
				+ "where lower(ques.category) like lower(:category) "
				+ ") and lower(ans.ans) like lower(:keyword)"),
		@NamedQuery(name = "getAnswersByQuestionQuery", query = "from AnswerEntity ans "
				+ "where ans.question.quesId = :quesId"),
		@NamedQuery(name = "deleteAnswerQuery", query = "delete from AnswerEntity ans " 
				+ "where ans.ansId = :ansId"),
		@NamedQuery(name = "deleteAnsByQuesIdQuery", query = "delete from AnswerEntity ans "
				+ "where ans.question.quesId = :quesId"),
		@NamedQuery(name = "deleteAnsByUserIdQuery", query = "delete from AnswerEntity ans "
				+ "where ans.user.userId = :userId"),
		@NamedQuery(name = "getAnsByQuesIdQuery", query = "from AnswerEntity ans "
				+ "where ans.question.quesId = :quesId "),
		@NamedQuery(name = "getAnswersByUserQuery", query = "from AnswerEntity ans "
				+ "where ans.user.userId = :userId"),
		@NamedQuery(name = "getAnswerByQuesAnsPairQuery", query = "from AnswerEntity ans "
				+ "where ans.ansId = :ansId and ans.question.quesId = :quesId") })
public class AnswerEntity {
	@Id
	@Column(name = "ans_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "AnswerSequence", sequenceName = "answer_sequence")
	private Long ansId;

	@Column(name = "ans_str", length = 2000)
	private String ans;

	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "ques_id")
	private QuestionEntity question;

	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "answered_by")
	private UserEntity user;

	@Transient
	@JsonProperty("quesId")
	private Long quesId;

	@Transient
	@JsonProperty("answeredBy")
	private Long answeredBy;

	@Transient
	@JsonProperty("likes")
	private Long likes;

	@Column(name = "ans_created_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date ansCreatedOn;

	@Column(name = "ans_modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date ansModifiedOn;

	public Long getAnsId() {
		return ansId;
	}

	public void setAnsId(Long ansId) {
		this.ansId = ansId;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public QuestionEntity getQuestion() {
		return question;
	}

	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Long getQuesId() {
		return quesId;
	}

	public void setQuesId(Long quesId) {
		this.quesId = quesId;
	}

	public Long getAnsweredBy() {
		return answeredBy;
	}

	public void setAnsweredBy(Long answeredBy) {
		this.answeredBy = answeredBy;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Date getAnsCreatedOn() {
		return ansCreatedOn;
	}

	public void setAnsCreatedOn(Date ansCreatedOn) {
		this.ansCreatedOn = ansCreatedOn;
	}

	public Date getAnsModifiedOn() {
		return ansModifiedOn;
	}

	public void setAnsModifiedOn(Date ansModifiedOn) {
		this.ansModifiedOn = ansModifiedOn;
	}

}
