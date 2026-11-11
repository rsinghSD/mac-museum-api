package io.github.rsinghsd.macmuseum.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class WriteApiKeyInterceptorTest {

    private static final MockHttpServletResponse RESPONSE = new MockHttpServletResponse();

    @ParameterizedTest
    @ValueSource(strings = {"GET", "HEAD", "OPTIONS"})
    void readsAreAlwaysAllowed(String method) {
        var interceptor = new WriteApiKeyInterceptor(new WriteSecurityProperties("secret", false));

        assertThat(interceptor.preHandle(request(method, null), RESPONSE, new Object())).isTrue();
    }

    @Test
    void writesAreOpenWithoutKeyWhenAllowed() {
        var interceptor = new WriteApiKeyInterceptor(new WriteSecurityProperties("", true));

        assertThat(interceptor.preHandle(request("POST", null), RESPONSE, new Object())).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"POST", "PUT", "PATCH", "DELETE"})
    void writesAreDisabledWithoutKeyWhenNotAllowed(String method) {
        var interceptor = new WriteApiKeyInterceptor(new WriteSecurityProperties(null, false));

        assertThatThrownBy(() -> interceptor.preHandle(request(method, null), RESPONSE, new Object()))
                .isInstanceOf(WritesDisabledException.class);
    }

    @Test
    void rejectsMissingKey() {
        var interceptor = new WriteApiKeyInterceptor(new WriteSecurityProperties("secret", true));

        assertThatThrownBy(() -> interceptor.preHandle(request("DELETE", "  "), RESPONSE, new Object()))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("X-API-Key");
    }

    @Test
    void rejectsWrongKey() {
        var interceptor = new WriteApiKeyInterceptor(new WriteSecurityProperties("secret", true));

        assertThatThrownBy(() -> interceptor.preHandle(request("PUT", "secreT"), RESPONSE, new Object()))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("The API key is not valid.");
    }

    @Test
    void acceptsCorrectKey() {
        var interceptor = new WriteApiKeyInterceptor(new WriteSecurityProperties("secret", false));

        assertThat(interceptor.preHandle(request("POST", "secret"), RESPONSE, new Object())).isTrue();
    }

    private static MockHttpServletRequest request(String method, String apiKey) {
        var request = new MockHttpServletRequest(method, "/api/v1/models");
        if (apiKey != null) {
            request.addHeader(WriteApiKeyInterceptor.HEADER, apiKey);
        }
        return request;
    }
}
