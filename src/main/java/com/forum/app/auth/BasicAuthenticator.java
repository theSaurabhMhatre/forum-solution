package com.forum.app.auth;

import java.util.Optional;

import com.forum.app.exception.ForumException;
import com.forum.mod.user.factory.UserBusinessFactory;
import com.forum.mod.user.service.UserEntity;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

/**
 * This is the authenticator class which implements basic authentication
 * for securing the API end points. It authenticates requests from both,
 * the user interface and/or applications as well as logged in users.
 *
 * @author Saurabh Mhatre
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, UserEntity> {
    private UserBusinessFactory businessFactory;
    private String appId;
    private String appPswd;

    public BasicAuthenticator(UserBusinessFactory businessFactory) {
        this.businessFactory = businessFactory;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppPswd(String appPswd) {
        this.appPswd = appPswd;
    }

    @Override
    @UnitOfWork
    public Optional<UserEntity> authenticate(BasicCredentials credentials) throws AuthenticationException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(credentials.getUsername());
        userEntity.setUserPswd(credentials.getPassword());
        // check if request has come from UI in case of non-logged in
        // users using properties defined in configuration file for now
        // this can be augmented to accommodate requests coming from multiple
        // applications, in which case authentication can be done from
        // a separate table meant to store application Id's and passwords
        // for registered applications
        if (userEntity.getUserName().equals(appId) && userEntity.getUserPswd().equals(appPswd)) {
            return Optional.of(userEntity);
        }
        // request has come on behalf of a logged in user, authenticate
        // by querying database table corresponding to registered users
        try {
            userEntity = businessFactory.validateUser(userEntity.getUserName(), userEntity);
            return Optional.of(userEntity);
        } catch (ForumException ex) {
            return Optional.empty();
        }
    }

}
