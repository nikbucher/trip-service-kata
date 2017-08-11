package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    private static final ArrayList<Trip> NO_TRIPS = new ArrayList<Trip>();

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        validateThatUserIsLoggedInOrThrowException();

        return user.isFriendOf(getLoggedInUser())
                ? getTripsOfFriendOfLoggedUser(user)
                : NO_TRIPS;
    }

    private void validateThatUserIsLoggedInOrThrowException() {
        if (getLoggedInUser() == null) {
            throw new UserNotLoggedInException();
        }
    }

    protected List<Trip> getTripsOfFriendOfLoggedUser(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
