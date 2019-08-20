package com.forum.mod.answer.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.forum.app.key.AnswerLikeKey;

/**
 * This class represents the AnswerLikeEntity POJO and the mapping of the same
 * to the corresponding database table. It also contains queries used by
 * AnswerLikeRepo to query the table corresponding to AnswerLikeEntity.
 * 
 * @author Saurabh Mhatre
 */
@Entity
@Table(name = "table_answer_like")
@NamedQueries({
		@NamedQuery(name = "getLikesByAnswersByQuestionQuery", query = "select count(*) as likes, "
				+ "ansLike.ansLikeKey.answer.ansId " 
				+ "from AnswerLikeEntity ansLike "
				+ "where ansLike.ansLikeKey.question.quesId = :quesId " 
				+ "group by ansLike.ansLikeKey.answer.ansId "
				+ "order by likes"),
		@NamedQuery(name = "getLikesByAnswerQuery", query = "select count(*) as likes "
				+ "from AnswerLikeEntity ansLike " 
				+ "where ansLike.ansLikeKey.answer.ansId = :ansId"),
		@NamedQuery(name = "getAnswersLikedByUserQuery", query = "select ansLike.ansLikeKey.answer.ansId "
				+ "from AnswerLikeEntity ansLike " 
				+ "where ansLike.ansLikeKey.user.userId = :userId"),
		@NamedQuery(name = "deleteAnsLikesByAnsIdQuery", query = "delete from AnswerLikeEntity ansLike "
				+ "where ansLike.ansLikeKey.answer.ansId = :ansId"),
		@NamedQuery(name = "deleteAnsLikesByQuesIdQuery", query = "delete from AnswerLikeEntity ansLike "
				+ "where ansLike.ansLikeKey.question.quesId = :quesId"),
		@NamedQuery(name = "deleteAnsLikesByUserIdQuery", query = "delete from AnswerLikeEntity ansLike "
				+ "where ansLike.ansLikeKey.user.userId = :userId") })
@NamedNativeQueries({
		@NamedNativeQuery(name = "getMostLikedAnsByQuestionsQuery", query = "select second.ans_id, second.ques_id, "
				+ "second.x as likes from ( " 
				+ "select ques_id, max(x) as y " 
				+ "from ( "
				+ "select ans_id, ques_id, count(*) as x " 
				+ "from table_answer_like " 
				+ "group by ques_id, ans_id "
				+ "order by ques_id, x desc " 
				+ ") group by ques_id " 
				+ ") as first, "
				+ "( select ans_id, ques_id, count(*) as x " 
				+ "from table_answer_like " 
				+ "group by ques_id, ans_id "
				+ "order by ques_id, x desc " 
				+ ") as second " 
				+ "where first.ques_id = second.ques_id "
				+ "and first.y = second.x"),
		@NamedNativeQuery(name = "getLikesByAnswersQuery", query = "select count(*) as likes, ans_id  "
				+ "from table_answer_like " 
				+ "where ans_id in ( " 
				+ "select ans.ans_id "
				+ "from table_answer ans, table_question ques " 
				+ "where ans.ques_id = ques.ques_id "
				+ "and lower(ans.ans_str) like lower(:keyword) "
				+ "and lower(ques.ques_category) like lower(:category) " 
				+ ") group by ans_id"),
		@NamedNativeQuery(name = "getUsersAnswersLikesQuery", query = "select count(*) as aLikes "
				+ "from table_answer ans, table_answer_like likes " 
				+ "where ans.ans_id = likes.ans_id and "
				+ "ans.answered_by = :userId"),
		@NamedNativeQuery(name = "getAllUsersAnswersLikesQuery", query = "select count(*), x.user_id "
				+ "from table_answer ans, table_answer_like likes, table_user x "
				+ "where ans.ans_id = likes.ans_id and ans.answered_by = x.user_id " + "group by x.user_id "
				+ "order by x.user_id") })
public class AnswerLikeEntity {
	@EmbeddedId
	@JsonIgnore
	private AnswerLikeKey ansLikeKey;

	@Column(name = "ans_liked_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date ansLikedOn;

	@Transient
	@JsonProperty("ansId")
	private Long ansId;

	@Transient
	@JsonProperty("quesId")
	private Long quesId;

	@Transient
	@JsonProperty("userId")
	private Long userId;

	public AnswerLikeKey getAnsLikeKey() {
		return ansLikeKey;
	}

	public void setAnsLikeKey(AnswerLikeKey ansLikeKey) {
		this.ansLikeKey = ansLikeKey;
	}

	public Long getAnsId() {
		return ansId;
	}

	public void setAnsId(Long ansId) {
		this.ansId = ansId;
	}

	public Long getQuesId() {
		return quesId;
	}

	public void setQuesId(Long quesId) {
		this.quesId = quesId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getAnsLikedOn() {
		return ansLikedOn;
	}

	public void setAnsLikedOn(Date ansLikedOn) {
		this.ansLikedOn = ansLikedOn;
	}

}
