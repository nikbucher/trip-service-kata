package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.LoggedInUserProvider;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.craftedsw.tripservicekata.trip.UserBuilder.anUser;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TripServiceTest {

    private static final User UNUSED_USER = null;
    private static final User GUEST = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();

    private static final Trip TRIP_TO_STGALLEN = new Trip();
    private static final Trip TRIP_TO_BERN = new Trip();
    private User loggedInUser;
    private final TripService sut = new TestableTripService(() -> loggedInUser);

    @Before
    public void setUp() throws Exception {
        loggedInUser = REGISTERED_USER;
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_exception_if_user_not_logged_in() {
        // given:
        loggedInUser = GUEST;
        // when:
        sut.getTripsByUser(UNUSED_USER);
    }

    @Test
    public void should_get_any_trips_if_users_are_not_friends() {
        // given:
        final User user = anUser()
                .withFriends(ANOTHER_USER)
                .withTrips(TRIP_TO_STGALLEN)
                .build();
        // when:
        final List<Trip> result = sut.getTripsByUser(user);
        //then:
        assertEquals(result.size(), 0);
    }

    @Test
    public void should_get_trip_list_of_fried_if_users_are_friends() {
        // given:
        final User friendOfRegisteredUser = anUser()
                .withFriends(ANOTHER_USER, REGISTERED_USER)
                .withTrips(TRIP_TO_STGALLEN, TRIP_TO_BERN)
                .build();
        // when:
        final List<Trip> result = sut.getTripsByUser(friendOfRegisteredUser);
        //then:
        assertThat(result, hasItems(TRIP_TO_STGALLEN, TRIP_TO_BERN));
    }

    private class TestableTripService extends TripService {

        private TestableTripService(LoggedInUserProvider loggedInUserProvider) {
            super(loggedInUserProvider);
        }

        @Override
        protected List<Trip> getTripsOfFriendOfLoggedUser(User user) {
            return user.trips();
        }
    }
}
