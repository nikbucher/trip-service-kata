package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.LoggedInUserProvider;
import org.craftedsw.tripservicekata.user.User;

import java.util.List;

import static java.util.Collections.emptyList;

public class TripService {

    private static final List<Trip> NO_TRIPS = emptyList();

    private final LoggedInUserProvider loggedInUserProvider;
    private final TripRepository tripRepository;

    public TripService(LoggedInUserProvider loggedInUserProvider, TripRepository tripRepository) {
        this.loggedInUserProvider = loggedInUserProvider;
        this.tripRepository = tripRepository;
    }

    public List<Trip> getFriendTrips(User user) throws UserNotLoggedInException {
        if (getLoggedInUser() == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendOf(getLoggedInUser())
                ? tripsByUser(user)
                : NO_TRIPS;
    }

    private User getLoggedInUser() {
        return loggedInUserProvider.getUser();
    }

    private List<Trip> tripsByUser(User user) {
        return tripRepository.findByUser(user);
    }

}
