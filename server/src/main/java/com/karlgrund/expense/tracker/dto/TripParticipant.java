package com.karlgrund.expense.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TripParticipant {
    private String tripName;
    private String userEmail;

    @JsonCreator
    public TripParticipant(
        @JsonProperty("trip_name") String tripName,
        @JsonProperty("user_email") String userEmail
    ) {
        this.tripName = tripName;
        this.userEmail = userEmail;
    }

    @JsonIgnore
    public String getTripName() {
        return tripName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
