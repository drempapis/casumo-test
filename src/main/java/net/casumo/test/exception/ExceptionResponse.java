package net.casumo.test.exception;

public final class ExceptionResponse {

    private final String message;

    private final String status;

    public ExceptionResponse(final String status, final String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
