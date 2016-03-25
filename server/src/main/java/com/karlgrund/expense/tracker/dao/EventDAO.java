package com.karlgrund.expense.tracker.dao;

import com.karlgrund.expense.tracker.dto.Event;
import com.karlgrund.expense.tracker.mapper.EventMapper;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(EventMapper.class)
public abstract class EventDAO {

    @SqlQuery("SELECT * FROM event")
    public abstract List<Event> findAll();

    @SqlQuery("SELECT * FROM event "
        + "where event_name = :eventName")
    public abstract Event findEvent(@Bind("eventName") String eventName);

    @SqlUpdate("INSERT INTO event(event_name) VALUES(:eventName)")
    public abstract void store(@BindBean Event event);

}
