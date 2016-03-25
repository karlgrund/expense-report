package com.karlgrund.expense.tracker.resources;

import com.codahale.metrics.annotation.Timed;
import com.karlgrund.expense.tracker.dao.UserDAO;
import com.karlgrund.expense.tracker.dto.User;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("admin")
public class AdministratorResource {
    private UserDAO userDAO;

    public AdministratorResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @POST
    @Timed
    @Path("user")
    public Response createUser(
        @Valid User user
    ) {
        if (userDAO.findUserByEmail(user.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        userDAO.store(user, user.isAdmin());
        return Response.ok(user).build();
    }
}
