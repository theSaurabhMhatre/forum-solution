package com.forum.mod.question.service;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.forum.app.key.QuestionLikeKey;

@Entity
@Table(name = "table_question_like")
@NamedQueries({
		@NamedQuery(name = "getLikesByQuestionsQuery", query = "select count(*) as likes, "
				+ "quesLike.quesLikeKey.question.quesId "
				+ "from QuestionLikeEntity quesLike "
				+ "group by quesLike.quesLikeKey.question.quesId "
				+ "order by likes"),
		@NamedQuery(name = "getLikesByQuestionQuery", query = "select count(*) as likes "
				+ "from QuestionLikeEntity quesLike "
				+ "where quesLike.quesLikeKey.question.quesId = :quesId"),
		@NamedQuery(name = "getQuestionsLikedByUserQuery", query = "select quesLike.quesLikeKey.question.quesId "
				+ "from QuestionLikeEntity quesLike "
				+ "where quesLike.quesLikeKey.user.userId = :userId"),
		@NamedQuery(name = "deleteQuesLikesByQuesIdQuery", query = "delete from QuestionLikeEntity quesLike "
				+ "where quesLike.quesLikeKey.question.quesId = :quesId"),
		@NamedQuery(name = "deleteQuesLikesByUserIdQuery", query = "delete from QuestionLikeEntity quesLike "
				+ "where quesLike.quesLikeKey.user.userId = :userId") })
@NamedNativeQueries({ 
		@NamedNativeQuery(name = "getUsersQuestionsLikesQuery", query = "select count(*) as qLikes "
				+ "from table_question ques, table_question_like likes "
				+ "where ques.ques_id = likes.ques_id and "
				+ "ques.asked_by = :userId"),
		@NamedNativeQuery(name = "getAllUsersQuestionsLikesQuery", query = "select count(*), x.user_id "
				+ "from table_question ques, table_question_like likes, table_user x "
				+ "where ques.ques_id = likes.ques_id and ques.asked_by = x.user_id "
				+ "group by x.user_id "
				+ "order by x.user_id") })
public class QuestionLikeEntity {
	@EmbeddedId
	@JsonIgnore
	private QuestionLikeKey quesLikeKey;

	@Transient
	@JsonProperty(value = "quesId")
	private Long quesId;

	@Transient
	@JsonProperty(value = "userId")
	private Long userId;

	public QuestionLikeKey getQuesLikeKey() {
		return quesLikeKey;
	}

	public void setQuesLikeKey(QuestionLikeKey quesLikeKey) {
		this.quesLikeKey = quesLikeKey;
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

}
