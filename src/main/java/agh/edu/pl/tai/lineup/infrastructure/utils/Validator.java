package agh.edu.pl.tai.lineup.infrastructure.utils;

import agh.edu.pl.tai.lineup.infrastructure.utils.exceptions.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

public class Validator {

    private final List<Log> logs;

    public Validator() {
        logs = new ArrayList<>();
    }

    public Validator on(Boolean condition, String header, String error) {
        logs.add(new Log(condition, header, error));
        return this;
    }

    public <T> Validator onNull(T object, String header) {
        logs.add(new Log(object != null, header, "isNull"));
        return this;
    }

    public void validateAndThrow() {

        Map<String, List<Error>> errorLog = new HashMap<>();

        logs.forEach(log -> {
            Optional<Error> optional = log.validate();
            if (optional.isPresent()) {
                errorLog.putIfAbsent(log.header, new LinkedList<>());
                errorLog.get(log.header).add(optional.get());
            }
        });

        if (!errorLog.isEmpty()) throw new ValidationException(asString(errorLog));
    }

    private String asString(Map<String, List<Error>> errorLog) {
        StringBuilder errorMsg = new StringBuilder();
        errorLog.forEach((header, errors) -> errorMsg.append(header).append(": ").append(returnErrorMessageInPrettyFormat(errors)).append("\n"));
        return errorMsg.toString();
    }

    private List<String> returnErrorMessageInPrettyFormat(List<Error> errors) {
        return errors.stream().map(Error::getMessage).collect(Collectors.toList());
    }

    private final class Log {
        private final Boolean condition;
        private final String header;
        private final String error;

        private Log(Boolean condition, String header, String error) {
            this.condition = condition;
            this.header = header;
            this.error = error;
        }

        private Optional<Error> validate() {
            if (!condition)
                return Optional.of(new Error(error));
            else {
                return Optional.empty();
            }
        }

    }

    public List<Log> getLogs() {
        return Collections.unmodifiableList(logs);
    }
}

