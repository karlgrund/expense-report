package com.karlgrund.expense.tracker.resources;

import com.karlgrund.expense.tracker.dao.UserDAO;
import com.karlgrund.expense.tracker.dto.User;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("user")
public class UserResource {
    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    public List<User> getUser(
        @QueryParam("email") Optional<String> email
    ) {
        if (!email.isPresent()) {
            return userDAO.findAll();
        }
        return Arrays.asList(userDAO.findUserByEmail(email.get()));
    }
}
