package com.forum.mod.question.service;

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
import com.forum.mod.user.service.UserEntity;

/**
 * This class represents the QuestionEntity POJO and the mapping of the same to
 * the corresponding database table. It also contains queries used by
 * QuestionRepo to query the table corresponding to QuestionEntity.
 * 
 * @author Saurabh Mhatre
 *
 */
@Entity
@Table(name = "table_question")
@NamedQueries({
		@NamedQuery(name = "getQuestionsQuery", query = "from QuestionEntity ques "
				+ "where lower(ques.ques) like lower(:keyword) " 
				+ "and lower(ques.category) like lower(:category) "
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

	@Column(name = "ques_str", length = 1000)
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

	@Column(name = "ques_created_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date quesCreatedOn;

	@Column(name = "ques_modified_on")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date quesModifiedOn;

	public enum Category {
		TECHNOLOGY("TECHNOLOGY"), SCIENCE("SCIENCE"), HISTORY("HISTORY"), COMICS("COMICS"), OTHERS("OTHERS"), ALL(
				"ALL");

		private String value;

		private Category(String value) {
			this.value = value;
		}

		public String getCategory() {
			return this.value.toLowerCase();
		}

	}

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

	public Date getQuesCreatedOn() {
		return quesCreatedOn;
	}

	public void setQuesCreatedOn(Date quesCreatedOn) {
		this.quesCreatedOn = quesCreatedOn;
	}

	public Date getQuesModifiedOn() {
		return quesModifiedOn;
	}

	public void setQuesModifiedOn(Date quesModifiedOn) {
		this.quesModifiedOn = quesModifiedOn;
	}

}
