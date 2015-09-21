package net.casumo.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
public class InternalErrorException extends RuntimeException {

    public InternalErrorException() { super(); }

    public InternalErrorException(final String message) { super(message); }

    public InternalErrorException(final Exception e) { super(e); }

    public InternalErrorException(final String message, final Exception e) { super(message, e); }

}
