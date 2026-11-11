package io.github.rsinghsd.macmuseum.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ProblemDetail;

/** Covers handler branches that are hard to trigger through the HTTP layer. */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void unexpectedErrorsDoNotLeakDetails() {
        ProblemDetail problem = handler.handleUnexpected(new IllegalStateException("db password is hunter2"));

        assertThat(problem.getStatus()).isEqualTo(500);
        assertThat(problem.getDetail()).isEqualTo("An unexpected error occurred.").doesNotContain("hunter2");
    }

    @Test
    void integrityViolationsBecomeConflicts() {
        ProblemDetail problem = handler.handleIntegrity(new DataIntegrityViolationException("uk_apple_model_slug"));

        assertThat(problem.getStatus()).isEqualTo(409);
        assertThat(problem.getDetail()).doesNotContain("uk_apple_model_slug");
    }
}
