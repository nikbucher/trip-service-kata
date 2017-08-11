package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TripServiceTest {

    private static final User USER_WITHOUT_FRIENDS = new User();
    private static final User NO_USER = null;
    private static final User NOT_LOGGED_IN_USER = null;
    private static final User LOGGED_IN_USER = new User();
    private static final User FRIEND_OF_LOGGED_IN_USER = new User();
    private static final Trip TRIP_TO_STGALLEN = new Trip();

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_exception_if_user_not_logged_in() {
        // given:
        final TripService sut = new TripServiceWithNotLoggedInUser();
        // when:
        sut.getTripsByUser(NO_USER);
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_exception_if_user_is_logged_no_user_is_passed() {
        // given:
        final TripService sut = new TripServiceWithLoggedInUser();
        // when:
        sut.getTripsByUser(NO_USER);
    }

    @Test
    public void should_get_empty_list_if_LoggedInUser_is_no_friend_of_givenUser() {
        // given:
        final TripService sut = new TripServiceWithLoggedInUser();
        // when:
        final List<Trip> result = sut.getTripsByUser(USER_WITHOUT_FRIENDS);
        //then:
        assertEquals(result.size(), 0);
    }

    @Test
    public void should_get_trip_list_of_fried_of_logged_in_user() {
        // given:
        FRIEND_OF_LOGGED_IN_USER.addFriend(new User());
        FRIEND_OF_LOGGED_IN_USER.addFriend(LOGGED_IN_USER);
        FRIEND_OF_LOGGED_IN_USER.addTrip(TRIP_TO_STGALLEN);

        final TripService sut = new TripServiceWithLoggedInUserHavingFriend();
        // when:
        final List<Trip> result = sut.getTripsByUser(FRIEND_OF_LOGGED_IN_USER);
        //then:
        assertThat(result.size(), is(1));
        assertThat(result.get(0), equalTo(TRIP_TO_STGALLEN));
    }

    private static class TripServiceWithNotLoggedInUser extends TripService {
        @Override
        protected User getLoggedInUser() {
            return NOT_LOGGED_IN_USER;
        }
    }

    private static class TripServiceWithLoggedInUser extends TripService {
        @Override
        protected User getLoggedInUser() {
            return LOGGED_IN_USER;
        }
    }

    private static class TripServiceWithLoggedInUserHavingFriend extends TripService {
        @Override
        protected User getLoggedInUser() {
            return LOGGED_IN_USER;
        }

        @Override
        protected List<Trip> getTripsOfFriendOfLoggedUser(User user) {
            return FRIEND_OF_LOGGED_IN_USER.trips();
        }
    }
}
