package com.karlgrund.expense.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventParticipant {
    private String eventName;
    private String userEmail;

    @JsonCreator
    public EventParticipant(
        @JsonProperty("event_name") String eventName,
        @JsonProperty("user_email") String userEmail
    ) {
        this.eventName = eventName;
        this.userEmail = userEmail;
    }

    @JsonIgnore
    public String getEventName() {
        return eventName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
