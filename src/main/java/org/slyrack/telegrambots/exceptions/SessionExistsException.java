package org.slyrack.telegrambots.exceptions;

public class SessionExistsException extends RuntimeException {
    public SessionExistsException() {
    }

    public SessionExistsException(String message) {
        super(message);
    }

    public SessionExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionExistsException(Throwable cause) {
        super(cause);
    }

    public SessionExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
