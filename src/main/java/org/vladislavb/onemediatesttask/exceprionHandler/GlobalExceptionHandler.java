package org.vladislavb.onemediatesttask.exceprionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler is a centralized exception handler for the application.
 * It intercepts exceptions thrown by controllers and returns appropriate HTTP responses
 * with corresponding status codes and error messages.
 * This class uses Spring's @ControllerAdvice annotation to handle exceptions globally.
 *
 * @author Vladislav Baryshev
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles AuthenticationException and returns a "FORBIDDEN" HTTP status code (403)
     * along with an error message indicating authentication failure.
     *
     * @param ex the AuthenticationException that was thrown.
     * @return a ResponseEntity containing the error message and the 403 Forbidden status.
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication Failed" + ex.getMessage());
    }


    /**
     * Handles any other generic exceptions and returns an "INTERNAL_SERVER_ERROR" HTTP status code (500)
     * along with a generic error message.
     *
     * @param ex the Exception that was thrown.
     * @return a ResponseEntity containing the error message and the 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred" + ex.getMessage());
    }

    /**
     * Handles validation exceptions and returns a structured error response.
     *
     * @param ex ConstraintViolationException instance.
     * @return ResponseEntity containing validation error details.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        Map<String, String> errors = violations.stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing
                ));

        return ResponseEntity.badRequest().body(errors);
    }
}
