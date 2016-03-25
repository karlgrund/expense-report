package com.karlgrund.expense.tracker.manager;

import com.karlgrund.expense.tracker.dao.TripDAO;
import com.karlgrund.expense.tracker.dao.TripParticipationDAO;
import com.karlgrund.expense.tracker.dao.UserDAO;
import com.karlgrund.expense.tracker.dto.Trip;
import com.karlgrund.expense.tracker.dto.TripParticipant;
import java.util.List;
import javax.ws.rs.BadRequestException;

public class TripManager {
    private TripDAO tripDAO;
    private TripParticipationDAO tripParticipationDAO;
    private UserDAO userDAO;

    public TripManager(TripDAO tripDAO, TripParticipationDAO tripParticipationDAO, UserDAO userDAO) {
        this.tripDAO = tripDAO;
        this.tripParticipationDAO = tripParticipationDAO;
        this.userDAO = userDAO;
    }

    public void storeTrip(Trip trip) {
        if (tripDAO.findTrip(trip.getTripName()) != null) {
            throw new BadRequestException("Trip name already exists");
        }

        if (!trip.getParticipantEmails().isEmpty()) {
            if (trip.getParticipantEmails()
                .stream()
                .allMatch(
                    email -> userDAO.findUserByEmail(email) != null)
                ) {
                tripDAO.store(trip);

                trip.getParticipantEmails()
                    .stream()
                    .forEach(email -> addParticipant(new TripParticipant(trip.getTripName(), email)));

            } else {
                throw new BadRequestException("User does not exist");
            }
        } else {
            tripDAO.store(trip);
        }

    }

    public void validateTrip(String tripName) {
        findTrip(tripName);
    }

    public Trip findTrip(String tripName) {
        Trip trip = tripDAO.findTrip(tripName);
        if (trip == null) {
            throw new BadRequestException("Trip does not exist");
        }
        return trip;
    }

    public void addParticipant(TripParticipant participant) {
        if (tripDAO.findTrip(participant.getTripName()) == null) {
            throw new BadRequestException("Trip does not exist");
        }
        if (userDAO.findUserByEmail(participant.getUserEmail()) == null) {
            throw new BadRequestException("User does not exist");
        }
        if (tripDAO.findTrip(participant.getTripName())
            .getParticipantEmails()
            .contains(participant.getUserEmail())
            ) {
            throw new BadRequestException("User already in trip");
        }
        tripParticipationDAO.store(participant);
    }

    public List<Trip> findAll() {
        return tripDAO.findAll();
    }
}
