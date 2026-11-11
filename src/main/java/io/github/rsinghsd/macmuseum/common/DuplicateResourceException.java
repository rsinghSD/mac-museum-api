package io.github.rsinghsd.macmuseum.common;

/** Thrown when creating or renaming a resource would violate a uniqueness rule. Mapped to HTTP 409. */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
