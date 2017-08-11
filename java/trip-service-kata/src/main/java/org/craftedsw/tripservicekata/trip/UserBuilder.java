package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

public final class UserBuilder {
    private Trip[] trips = new Trip[0];
    private User[] friends = new User[0];

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withTrips(Trip... trips) {
        this.trips = trips;
        return this;
    }

    public UserBuilder withFriends(User... friends) {
        this.friends = friends;
        return this;
    }

    public User build() {
        final User user = new User();
        addTripsTo(user);
        addFriendsTo(user);
        return user;
    }

    private void addTripsTo(User user) {
        for (Trip t : trips)
            user.addTrip(t);
    }

    private void addFriendsTo(User user) {
        for (User f : friends)
            user.addFriend(f);
    }
}
