package com.mentor.rateLimiter.exception;

public class LoginRateLimitException extends RuntimeException {
    public LoginRateLimitException(String message) {
        super(message);
    }

    public LoginRateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
