package io.github.rsinghsd.macmuseum.common;

/** Thrown when a requested resource does not exist. Mapped to HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException model(String slug) {
        return new ResourceNotFoundException("No model found with slug '" + slug + "'");
    }
}
