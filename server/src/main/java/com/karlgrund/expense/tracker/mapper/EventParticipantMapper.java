package com.karlgrund.expense.tracker.mapper;

import com.karlgrund.expense.tracker.dto.EventParticipant;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class EventParticipantMapper implements ResultSetMapper<EventParticipant> {
    @Override
    public EventParticipant map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new EventParticipant(
            r.getString("event_name"),
            r.getString("user_email")
        );
    }
}
