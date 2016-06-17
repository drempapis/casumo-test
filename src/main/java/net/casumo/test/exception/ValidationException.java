package net.casumo.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Validation Exception")
public class ValidationException extends RuntimeException {

    public ValidationException(final String message, final Exception e) { super(message, e); }
}
