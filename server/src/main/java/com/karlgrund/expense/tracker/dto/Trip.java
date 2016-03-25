package com.karlgrund.expense.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotNull;

public class Trip {
    @NotNull
    private String tripName;
    private List<String> participantEmail;

    @JsonCreator
    public Trip(
        @JsonProperty("trip_name") String tripName,
        @JsonProperty("participants") List<String> participantEmail
    ) {
        this.tripName = tripName;
        this.participantEmail = participantEmail;
    }

    public boolean isParticipant(String userEmail) {
        return participantEmail.contains(userEmail);
    }

    public String getTripName() {
        return tripName;
    }

    public List<String> getParticipantEmails() {
        return participantEmail;
    }

    public void addParticipants(List<String> participantEmails) {
        this.participantEmail = participantEmails;
    }
}
