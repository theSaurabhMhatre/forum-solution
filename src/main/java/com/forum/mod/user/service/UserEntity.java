package com.forum.mod.user.service;

import java.security.Principal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.security.auth.Subject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forum.app.constant.ForumError;
import com.forum.app.exception.ForumException;

/**
 * This class represents the UserEntity POJO and the mapping of the same to the
 * corresponding database table. It also contains queries used by UserRepo to
 * query the table corresponding to UserEntity.
 * 
 * @author Saurabh Mhatre
 */
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
public class UserEntity implements Cloneable, Principal {
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

	@Column(name = "user_created_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date userCreatedOn;

	@Column(name = "user_modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date userModifiedOn;

	@Column(name = "user_avatar")
	private String userAvatar;

	/**
	 * This indicates the different user attributes which can be
	 * updated.
	 *
	 * @author Saurabh Mhatre
	 */
	public enum Attribute {
		USER_NAME("USER_NAME"),
		
		USER_PSWD("USER_PSWD"),
		
		USER_BIO("USER_BIO"),
		
		USER_MAIL("USER_MAIL"),
		
		USER_AVATAR("USER_AVATAR"),
		
		USER_SECRET("USER_SECRET");

		private String value;

		private Attribute(String value) {
			this.value = value;
		}

		public String getAttribute() {
			return this.value.toLowerCase();
		}

	}

	@Override
	@JsonIgnore
	public String getName() {
		return userName;
	}

	@Override
	public boolean implies(Subject subject) {
		return false;
	}

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

	public Date getUserCreatedOn() {
		return userCreatedOn;
	}

	public void setUserCreatedOn(Date userCreatedOn) {
		this.userCreatedOn = userCreatedOn;
	}

	public Date getUserModifiedOn() {
		return userModifiedOn;
	}

	public void setUserModifiedOn(Date userModifiedOn) {
		this.userModifiedOn = userModifiedOn;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public UserEntity cloneUser() throws ForumException {
		UserEntity userEntity = null;
		try {
			userEntity = (UserEntity) super.clone();
		} catch (Exception ex) {
			throw new ForumException(ForumError.MODIFY_ERROR.getMessage());
		}
		return userEntity;
	}

}
