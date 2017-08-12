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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class TripServiceTest {

    private static final User UNUSED_USER = null;
    private static final User GUEST = null;
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();

    private static final Trip TRIP_TO_STGALLEN = new Trip();
    private static final Trip TRIP_TO_BERN = new Trip();

    private final LoggedInUserProvider loggedInUserProvider = mock(LoggedInUserProvider.class);
    private final TripRepository tripRepository = mock(TripRepository.class);
    private final TripService sut = new TripService(loggedInUserProvider, tripRepository);

    @Before
    public void setUp() throws Exception {
        given(loggedInUserProvider.getUser()).willReturn(REGISTERED_USER);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_exception_if_user_not_logged_in() {
        // given:
        given(loggedInUserProvider.getUser()).willReturn(GUEST);
        // when:
        sut.getFriendTrips(UNUSED_USER);
    }

    @Test
    public void should_not_return_any_trips_when_users_are_not_friends() {
        // given:
        final User notAFriend = anUser()
                .withFriends(ANOTHER_USER)
                .withTrips(TRIP_TO_STGALLEN)
                .build();
        // when:
        final List<Trip> result = sut.getFriendTrips(notAFriend);
        //then:
        assertEquals(result.size(), 0);
    }

    @Test
    public void should_return_friends_tris_when_users_are_friends() {
        // given:
        final User friend = anUser()
                .withFriends(ANOTHER_USER, REGISTERED_USER)
                .withTrips(TRIP_TO_STGALLEN, TRIP_TO_BERN)
                .build();
        given(tripRepository.findByUser(friend)).willReturn(friend.trips());
        // when:
        final List<Trip> result = sut.getFriendTrips(friend);
        //then:
        assertThat(result, hasItems(TRIP_TO_STGALLEN, TRIP_TO_BERN));
    }

}
