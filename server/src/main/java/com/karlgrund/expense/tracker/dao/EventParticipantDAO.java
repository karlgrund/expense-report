package com.karlgrund.expense.tracker.dao;

import com.karlgrund.expense.tracker.dto.EventParticipant;
import com.karlgrund.expense.tracker.mapper.EventParticipantMapper;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

public abstract class EventParticipantDAO {

    @SqlQuery("SELECT user_email FROM event_participants where event_name = :eventName")
    public abstract List<String> findParticipants(@Bind("eventName") String eventName);

    @SqlUpdate("INSERT INTO event_participants(event_name, user_email) VALUES("
        + ":eventName, "
        + ":userEmail "
        + ")")
    @RegisterMapper(EventParticipantMapper.class)
    public abstract void store(@BindBean EventParticipant eventParticipant);
}
