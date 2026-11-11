package io.github.rsinghsd.macmuseum.common;

import io.github.rsinghsd.macmuseum.model.CpuArchitecture;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Translates exceptions into RFC 7807 {@link ProblemDetail} responses.
 * Spring MVC's own exceptions (malformed JSON, unsupported media type, ...) are handled by the
 * {@link ResponseEntityExceptionHandler} base class, which already produces problem details.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        return problem(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    ProblemDetail handleDuplicate(DuplicateResourceException ex) {
        return problem(HttpStatus.CONFLICT, "Duplicate resource", ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    ProblemDetail handleBadRequest(BadRequestException ex) {
        return problem(HttpStatus.BAD_REQUEST, "Bad request", ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<ProblemDetail> handleUnauthorized(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "ApiKey header=\"" + WriteApiKeyInterceptor.HEADER + "\"")
                .body(problem(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage()));
    }

    @ExceptionHandler(WritesDisabledException.class)
    ProblemDetail handleWritesDisabled(WritesDisabledException ex) {
        return problem(HttpStatus.FORBIDDEN, "Writes disabled", ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ProblemDetail handleIntegrity(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation", ex);
        return problem(HttpStatus.CONFLICT, "Data integrity violation",
                "The request conflicts with existing data.");
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex);
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error",
                "An unexpected error occurred.");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        ProblemDetail body = problem(HttpStatus.BAD_REQUEST, "Validation failed",
                "One or more fields are invalid.");
        body.setProperty("errors", errors);
        return ResponseEntity.badRequest().headers(headers).body(body);
    }

    /** Query/path parameters that cannot be converted, e.g. {@code ?year=abc} or {@code ?cpu=z80}. */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            @NonNull TypeMismatchException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        String name = ex instanceof MethodArgumentTypeMismatchException mismatch
                ? mismatch.getName() : ex.getPropertyName();
        String detail = "Invalid value '" + ex.getValue() + "' for parameter '" + name + "'";
        Class<?> required = ex.getRequiredType();
        if (required == CpuArchitecture.class) {
            detail += ". Allowed values: " + CpuArchitecture.allowedValues();
        } else if (required != null) {
            detail += ". Expected type: " + required.getSimpleName();
        }
        ProblemDetail body = problem(HttpStatus.BAD_REQUEST, "Invalid parameter", detail);
        return ResponseEntity.badRequest().headers(headers).body(body);
    }

    private static ProblemDetail problem(HttpStatus status, String title, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        return problem;
    }
}
