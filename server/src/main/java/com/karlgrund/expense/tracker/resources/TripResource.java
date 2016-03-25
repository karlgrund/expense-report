package com.karlgrund.expense.tracker.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.karlgrund.expense.tracker.dto.Trip;
import com.karlgrund.expense.tracker.dto.TripParticipant;
import com.karlgrund.expense.tracker.manager.TripManager;
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
@Path("/trips")
public class TripResource {
    private TripManager tripManager;

    public TripResource(
        TripManager tripManager
    ) {
        this.tripManager = tripManager;
    }

    @GET
    public List<Trip> getTrips(
        @QueryParam("tripName") Optional<String> tripName
    ) {
        if (tripName.isPresent()) {
            return Collections.singletonList(tripManager.findTrip(tripName.get()));
        }
        return tripManager.findAll();
    }

    @POST
    public Trip createTrip(
        @Valid Trip trip
    ) {
        tripManager.storeTrip(trip);
        return trip;
    }

    @POST
    @Path("participant")
    public Trip addParticipant(
        @Valid TripParticipant participant
    ) {
        tripManager.addParticipant(participant);
        return getTrips(Optional.ofNullable(participant.getTripName())).get(0);
    }
}
