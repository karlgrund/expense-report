package com.karlgrund.expense.tracker.dao;

import com.karlgrund.expense.tracker.dto.Trip;
import com.karlgrund.expense.tracker.mapper.TripMapper;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TripMapper.class)
public abstract class TripDAO {

    @SqlQuery("SELECT * FROM trip")
    public abstract List<Trip> findAll();

    @SqlQuery("SELECT * FROM trip "
        + "where trip_name = :tripName")
    public abstract Trip findTrip(@Bind("tripName") String tripName);

    @SqlUpdate("INSERT INTO trip(trip_name) VALUES(:tripName)")
    public abstract void store(@BindBean Trip trip);

}
