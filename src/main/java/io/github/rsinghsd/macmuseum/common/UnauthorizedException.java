package io.github.rsinghsd.macmuseum.common;

/** Thrown when a write request has a missing or wrong API key. Mapped to HTTP 401. */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
