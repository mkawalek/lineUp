package agh.edu.pl.tai.lineup.infrastructure.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super("Entered Input is not valid : " + message);
    }

    public ValidationException() {
    }
}
