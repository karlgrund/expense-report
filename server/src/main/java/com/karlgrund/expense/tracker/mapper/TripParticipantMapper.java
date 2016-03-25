package com.karlgrund.expense.tracker.mapper;

import com.karlgrund.expense.tracker.dto.TripParticipant;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TripParticipantMapper implements ResultSetMapper<TripParticipant> {
    @Override
    public TripParticipant map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new TripParticipant(
            r.getString("trip_name"),
            r.getString("user_email")
        );
    }
}
