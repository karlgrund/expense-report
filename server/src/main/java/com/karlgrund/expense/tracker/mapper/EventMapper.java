package com.karlgrund.expense.tracker.mapper;

import com.karlgrund.expense.tracker.dao.EventParticipantDAO;
import com.karlgrund.expense.tracker.dto.Event;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class EventMapper implements ResultSetMapper<Event> {
    private static EventParticipantDAO eventParticipantDAO;

    public EventMapper() {

    }

    public EventMapper(EventParticipantDAO eventParticipantDAO) {
        this.eventParticipantDAO = eventParticipantDAO;
    }

    @Override
    public Event map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        String eventName = resultSet.getString("event_name");
        return new Event(
            eventName,
            eventParticipantDAO.findParticipants(eventName)
        );
    }
}
