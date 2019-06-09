package com.forum.mod.user.service;

import java.util.List;

import com.forum.app.constant.ForumError;
import com.forum.app.exception.DeleteException;
import com.forum.app.exception.FetchException;
import com.forum.app.exception.ModifyException;

/**
 * This class acts as an intermediate class between the UserBusinessFactory and
 * the UserRepo and throws exceptions in case something fails.
 * 
 * @author Saurabh Mhatre
 *
 */
public class UserService {
	private UserRepo userRepo;

	public UserService() {
		this.userRepo = new UserRepo();
	}

	public UserEntity getUser(Long userId) throws FetchException {
		try {
			UserEntity userEntity = userRepo.getUser(userId);
			if (userEntity != null) {
				return userEntity;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public UserEntity modifyUser(UserEntity userEntity) throws ModifyException {
		try {
			userEntity = userRepo.modifyUser(userEntity);
			if (userEntity != null) {
				return userEntity;
			} else {
				throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new ModifyException(ForumError.MODIFY_ERROR.getMessage());
		}
	}

	public Boolean deleteUser(Long userId) throws DeleteException {
		try {
			if (userRepo.deleteUser(userId)) {
				return true;
			} else {
				throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new DeleteException(ForumError.DELETE_ERROR.getMessage());
		}
	}

	public UserEntity validateUser(UserEntity userEntity) throws FetchException {
		try {
			userEntity = userRepo.validateUser(userEntity);
			if (userEntity != null) {
				return userEntity;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public UserEntity checkAvailability(String userName) throws FetchException {
		try {
			UserEntity userEntity = userRepo.checkAvailability(userName);
			if (userEntity != null) {
				return userEntity;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public List<Long> getUserIds() throws FetchException {
		try {
			List<Long> userIds = userRepo.getUserIds();
			if (userIds != null) {
				return userIds;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

	public List<Object> getAllUsers() throws FetchException {
		try {
			List<Object> users = userRepo.getAllUsers();
			if (users != null) {
				return users;
			} else {
				throw new FetchException(ForumError.FETCH_ERROR.getMessage());
			}
		} catch (Exception ex) {
			throw new FetchException(ForumError.FETCH_ERROR.getMessage());
		}
	}

}
