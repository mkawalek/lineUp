package agh.edu.pl.tai.lineup.domain.project.valueobject;

import agh.edu.pl.tai.lineup.infrastructure.utils.Validator;

public class ProjectId {

    private String value;

    public ProjectId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ProjectId of(String value) {
        validate(value);
        return new ProjectId(value);
    }

    private static void validate(String value) {
        new Validator()
                .onNull(value, "project.id")
                .validateAndThrow();

        new Validator()
                .on(!value.isEmpty(), "project.id", "empty")
                .validateAndThrow();
    }
}
