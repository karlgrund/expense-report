package com.karlgrund.expense.tracker.manager;

import com.karlgrund.expense.tracker.dao.EventDAO;
import com.karlgrund.expense.tracker.dao.EventParticipantDAO;
import com.karlgrund.expense.tracker.dao.UserDAO;
import com.karlgrund.expense.tracker.dto.Event;
import com.karlgrund.expense.tracker.dto.EventParticipant;
import java.util.List;
import javax.ws.rs.BadRequestException;

public class EventManager {
    private EventDAO eventDAO;
    private EventParticipantDAO eventParticipantDAO;
    private UserDAO userDAO;

    public EventManager(EventDAO eventDAO, EventParticipantDAO eventParticipantDAO, UserDAO userDAO) {
        this.eventDAO = eventDAO;
        this.eventParticipantDAO = eventParticipantDAO;
        this.userDAO = userDAO;
    }

    public void storeEvent(Event event) {
        if (eventDAO.findEvent(event.getEventName()) != null) {
            throw new BadRequestException("Event name already exists");
        }

        if (!event.getParticipantEmails().isEmpty()) {
            if (event.getParticipantEmails()
                .stream()
                .allMatch(
                    email -> userDAO.findUserByEmail(email) != null)
                ) {
                eventDAO.store(event);

                event.getParticipantEmails()
                    .stream()
                    .forEach(email -> addParticipant(new EventParticipant(event.getEventName(), email)));

            } else {
                throw new BadRequestException("User does not exist");
            }
        } else {
            eventDAO.store(event);
        }

    }

    public void validateEvent(String eventName) {
        findEvent(eventName);
    }

    public Event findEvent(String eventName) {
        Event event = eventDAO.findEvent(eventName);
        if (event == null) {
            throw new BadRequestException("Event does not exist");
        }
        return event;
    }

    public void addParticipant(EventParticipant participant) {
        if (eventDAO.findEvent(participant.getEventName()) == null) {
            throw new BadRequestException("Event does not exist");
        }
        if (userDAO.findUserByEmail(participant.getUserEmail()) == null) {
            throw new BadRequestException("User does not exist");
        }
        if (eventDAO.findEvent(participant.getEventName())
            .getParticipantEmails()
            .contains(participant.getUserEmail())
            ) {
            throw new BadRequestException("User already in event");
        }
        eventParticipantDAO.store(participant);
    }

    public List<Event> findAll() {
        return eventDAO.findAll();
    }
}
