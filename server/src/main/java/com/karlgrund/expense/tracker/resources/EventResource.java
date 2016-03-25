package com.karlgrund.expense.tracker.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.karlgrund.expense.tracker.dto.Event;
import com.karlgrund.expense.tracker.dto.EventParticipant;
import com.karlgrund.expense.tracker.manager.EventManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/events")
public class EventResource {
    private EventManager eventManager;

    public EventResource(
        EventManager eventManager
    ) {
        this.eventManager = eventManager;
    }

    @GET
    public List<Event> getEvents(
        @QueryParam("eventName") Optional<String> eventName
    ) {
        if (eventName.isPresent()) {
            return Collections.singletonList(eventManager.findEvent(eventName.get()));
        }
        return eventManager.findAll();
    }

    @POST
    public Event createEvent(
        @Valid Event event
    ) {
        eventManager.storeEvent(event);
        return event;
    }

    @POST
    @Path("participant")
    public Event addParticipant(
        @Valid EventParticipant participant
    ) {
        eventManager.addParticipant(participant);
        return getEvents(Optional.ofNullable(participant.getEventName())).get(0);
    }
}
