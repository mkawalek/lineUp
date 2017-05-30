package agh.edu.pl.tai.lineup.domain.joins.valueobject;

import agh.edu.pl.tai.lineup.infrastructure.utils.Validator;

public class JoinId {

    private String value;

    public JoinId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static JoinId of(String value) {
        validate(value);
        return new JoinId(value);
    }

    private static void validate(String value) {
        new Validator()
                .onNull(value, "join.id")
                .validateAndThrow();

        new Validator()
                .on(!value.isEmpty(), "join.id", "empty")
                .validateAndThrow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinId joinId = (JoinId) o;

        return value.equals(joinId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "JoinId{" +
                "value='" + value + '\'' +
                '}';
    }
}
