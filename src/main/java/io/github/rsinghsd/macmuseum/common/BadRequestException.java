package io.github.rsinghsd.macmuseum.common;

/** Thrown when a request is syntactically valid but semantically wrong. Mapped to HTTP 400. */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
