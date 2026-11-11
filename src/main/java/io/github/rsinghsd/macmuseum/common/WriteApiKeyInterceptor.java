package io.github.rsinghsd.macmuseum.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Set;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Guards write requests with a shared API key. Reads are always public.
 * Exceptions thrown here are turned into problem details by {@link GlobalExceptionHandler}.
 *
 * <p>A full Spring Security setup would be overkill for one shared secret; this keeps the
 * rule in one small, testable class.
 */
public class WriteApiKeyInterceptor implements HandlerInterceptor {

    public static final String HEADER = "X-API-Key";

    private static final Set<String> WRITE_METHODS = Set.of("POST", "PUT", "PATCH", "DELETE");

    private final WriteSecurityProperties properties;

    public WriteApiKeyInterceptor(WriteSecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (!WRITE_METHODS.contains(request.getMethod())) {
            return true;
        }
        if (!properties.hasKey()) {
            if (properties.allowUnauthenticatedWrites()) {
                return true;
            }
            throw new WritesDisabledException(
                    "Write operations are disabled because no API key is configured on this server.");
        }
        String provided = request.getHeader(HEADER);
        if (provided == null || provided.isBlank()) {
            throw new UnauthorizedException("Send the API key in the " + HEADER + " header.");
        }
        if (!constantTimeEquals(provided, properties.writeApiKey())) {
            throw new UnauthorizedException("The API key is not valid.");
        }
        return true;
    }

    /** Compares without leaking, through timing, how many leading characters matched. */
    private static boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }
}
