package com.karlgrund.expense.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotNull;

public class Event {
    @NotNull
    private String eventName;
    private List<String> participantEmail;

    @JsonCreator
    public Event(
        @JsonProperty("event_name") String eventName,
        @JsonProperty("participants") List<String> participantEmail
    ) {
        this.eventName = eventName;
        this.participantEmail = participantEmail;
    }

    public boolean isParticipant(String userEmail) {
        return participantEmail.contains(userEmail);
    }

    public String getEventName() {
        return eventName;
    }

    public List<String> getParticipantEmails() {
        return participantEmail;
    }

    public void addParticipants(List<String> participantEmails) {
        this.participantEmail = participantEmails;
    }
}
