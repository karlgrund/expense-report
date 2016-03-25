package com.karlgrund.expense.tracker.filter;

import com.karlgrund.expense.tracker.dao.UserDAO;
import com.karlgrund.expense.tracker.dto.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.java8.auth.Authenticator;
import java.util.Optional;

public class BasicAuthentication implements Authenticator<BasicCredentials, User> {
    private UserDAO userDAO;

    public BasicAuthentication(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

        User user = userDAO.findUserByEmail(credentials.getUsername());
        if (user.correctPassword(credentials.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
