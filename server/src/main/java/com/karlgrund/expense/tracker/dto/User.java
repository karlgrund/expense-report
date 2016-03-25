package com.karlgrund.expense.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User implements Principal {
    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;
    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;
    @Email
    @JsonProperty("email")
    private String email;
    @NotEmpty
    private String hashedPassword;
    @JsonProperty("admin")
    private Boolean isAdmin = false;

    public User(
        String firstName,
        String lastName,
        String email,
        String hashedPassword,
        boolean isAdmin
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.hashedPassword = hashedPassword;
    }

    @JsonCreator
    public static User create(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("password") String rawPassword,
        @JsonProperty("admin") Boolean isAdmin
    ) {
        return new User(
            firstName,
            lastName,
            email,
            hashPassword(rawPassword),
            isAdmin
        );
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public static String hashPassword(String password) {
        // TODO: Fix password auth (BCrypt)
        return Hashing.sha512().hashBytes(password.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return email;
    }

    public boolean correctPassword(String password) {
        return hashedPassword.equals(hashPassword(password));
    }
}
