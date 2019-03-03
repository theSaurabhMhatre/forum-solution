package com.forum.mod.user.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "table_user")
@NamedQueries({
		@NamedQuery(name = "checkAvailabilityQuery", query = "select user from UserEntity user "
				+ "where lower(user.userName) like :userName"),
		@NamedQuery(name = "validateUserQuery", query = "select user from UserEntity user "
				+ "where user.userName like :userName and "
				+ "user.userPswd like :userPswd"),
		@NamedQuery(name = "deleteUserQuery", query = "delete from UserEntity user "
				+ "where user.userId = :userId"),
		@NamedQuery(name = "getUserIdsQuery", query = "select user.userId from UserEntity user "
				+ "order by user.userId"),
		@NamedQuery(name = "getAllUsersQuery", query = "select user.userId, user.userName from UserEntity user "
				+ "order by user.userId") })
public class UserEntity implements Cloneable {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "UserSequence", sequenceName = "user_sequence")
	private Long userId;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "user_pswd")
	private String userPswd;

	@Column(name = "user_mail")
	private String userMail;

	@Column(name = "user_bio")
	private String userBio;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPswd() {
		return userPswd;
	}

	public void setUserPswd(String userPswd) {
		this.userPswd = userPswd;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserBio() {
		return userBio;
	}

	public void setUserBio(String userBio) {
		this.userBio = userBio;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		UserEntity userEntity = (UserEntity) super.clone();
		return userEntity;
	}

}
