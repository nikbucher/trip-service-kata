package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {

    private static final User FRIEND_OF_USER = new User();
    private static final User NO_FRIEND = new User();

    @Test
    public void should_return_isFriendOf_false_if_is_no_friend() throws Exception {
        // given:
        final User sut = new User();
        // when:
        final boolean result = sut.isFriendOf(NO_FRIEND);
        //then:
        assertThat(result, is(false));
    }

    @Test
    public void should_be_friend_of_user() throws Exception {
        // given:
        final User sut = new User();
        sut.addFriend(FRIEND_OF_USER);

        // when:
        final boolean result = sut.isFriendOf(FRIEND_OF_USER);

        //then:
        assertThat(result, is(true));
    }

}
