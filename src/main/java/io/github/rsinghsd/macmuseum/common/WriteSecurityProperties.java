package io.github.rsinghsd.macmuseum.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Protection for write endpoints (POST, PUT, PATCH, DELETE under {@code /api/v1}).
 *
 * @param writeApiKey                shared secret clients send in the {@code X-API-Key} header;
 *                                   blank means no key is configured
 * @param allowUnauthenticatedWrites when no key is configured: {@code true} leaves writes open
 *                                   (local development), {@code false} rejects them (production)
 */
@ConfigurationProperties("app.security")
public record WriteSecurityProperties(String writeApiKey, boolean allowUnauthenticatedWrites) {

    public boolean hasKey() {
        return writeApiKey != null && !writeApiKey.isBlank();
    }
}
