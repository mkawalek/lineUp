package agh.edu.pl.tai.lineup.domain.user.valueobject;

import agh.edu.pl.tai.lineup.infrastructure.utils.Validator;

public class UserId {

    private String value;

    public UserId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserId of(String value) {
        validateOnCreation(value);
        return new UserId(value);
    }

    private static void validateOnCreation(String value) {
        new Validator()
                .onNull(value, "UserId")
                .validateAndThrow();

        new Validator()
                .on(!value.isEmpty(), "UserId", "empty")
                .validateAndThrow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserId userId = (UserId) o;

        return value.equals(userId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "UserId{" +
                "value='" + value + '\'' +
                '}';
    }
}
