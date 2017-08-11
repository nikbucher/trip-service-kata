package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.List;

import static java.util.Collections.emptyList;

public class TripService {

    private static final List<Trip> NO_TRIPS = emptyList();

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (getLoggedInUser() == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendOf(getLoggedInUser())
                ? getTripsOfFriendOfLoggedUser(user)
                : NO_TRIPS;
    }

    protected List<Trip> getTripsOfFriendOfLoggedUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
