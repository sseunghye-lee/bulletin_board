package com.seung.pilot.commons.exception;

public class NotEnoughPointException extends RuntimeException {
    public NotEnoughPointException() {
    }

    public NotEnoughPointException(Throwable throwable) {
        super(throwable);
    }

    public NotEnoughPointException(String message) {
        super(message);
    }

    public NotEnoughPointException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
