package agh.edu.pl.tai.lineup.api.security;

import agh.edu.pl.tai.lineup.domain.user.valueobject.UserId;

public class AuthenticatedUser {

    private UserId userId;

    public AuthenticatedUser(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticatedUser that = (AuthenticatedUser) o;

        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return "AuthenticatedUser{" +
                "userId=" + userId +
                '}';
    }
}
