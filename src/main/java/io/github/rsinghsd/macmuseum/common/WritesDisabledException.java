package io.github.rsinghsd.macmuseum.common;

/** Thrown when writes are attempted but no API key is configured in production. Mapped to HTTP 403. */
public class WritesDisabledException extends RuntimeException {

    public WritesDisabledException(String message) {
        super(message);
    }
}
