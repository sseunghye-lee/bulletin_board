package com.seung.pilot.commons.exception;

/**
 * 토큰검사중에 문제가 생긴 경우 + error stack trace를 남기기 싫은 경우에 사용하는 exception클래스
 */
public class TokenFilterException extends RuntimeException {

    public TokenFilterException() {
    }

    public TokenFilterException(Throwable throwable) {
        super(throwable);
    }

    public TokenFilterException(String message) {
        super(message);
    }

    public TokenFilterException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

