package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.LoggedInUserProvider;
import org.craftedsw.tripservicekata.user.User;

import java.util.List;

import static java.util.Collections.emptyList;

public class TripService {

    private static final List<Trip> NO_TRIPS = emptyList();
    private final LoggedInUserProvider loggedInUserProvider;

    public TripService(LoggedInUserProvider loggedInUserProvider) {
        this.loggedInUserProvider = loggedInUserProvider;
    }

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (getLoggedInUser() == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendOf(getLoggedInUser())
                ? getTripsOfFriendOfLoggedUser(user)
                : NO_TRIPS;
    }

    private User getLoggedInUser() {
        return loggedInUserProvider.getUser();
    }

    protected List<Trip> getTripsOfFriendOfLoggedUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

}
