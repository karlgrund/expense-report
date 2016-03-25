package com.karlgrund.expense.tracker.mapper;

import com.karlgrund.expense.tracker.dao.TripParticipationDAO;
import com.karlgrund.expense.tracker.dto.Trip;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class TripMapper implements ResultSetMapper<Trip> {
    private static TripParticipationDAO tripParticipationDAO;

    public TripMapper() {

    }

    public TripMapper(TripParticipationDAO tripParticipationDAO) {
        this.tripParticipationDAO = tripParticipationDAO;
    }

    @Override
    public Trip map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        String tripName = resultSet.getString("trip_name");
        return new Trip(
            tripName,
            tripParticipationDAO.findParticipants(tripName)
        );
    }
}
