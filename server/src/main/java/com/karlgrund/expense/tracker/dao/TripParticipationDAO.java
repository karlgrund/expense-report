package com.karlgrund.expense.tracker.dao;

import com.karlgrund.expense.tracker.dto.TripParticipant;
import com.karlgrund.expense.tracker.mapper.TripParticipantMapper;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

public abstract class TripParticipationDAO {

    @SqlQuery("SELECT user_email FROM trip_participants where trip_name = :tripName")
    public abstract List<String> findParticipants(@Bind("tripName") String tripName);

    @SqlQuery("SELECT * FROM trip_participants where user_email = :userEmail")
    @RegisterMapper(TripParticipantMapper.class)
    public abstract List<TripParticipant> findFromUserEmail(@Bind("userEmail") String userEmail);

    @SqlUpdate("INSERT INTO trip_participants(trip_name, user_email) VALUES("
        + ":tripName, "
        + ":userEmail "
        + ")")
    @RegisterMapper(TripParticipantMapper.class)
    public abstract void store(@BindBean TripParticipant tripParticipant);
}
