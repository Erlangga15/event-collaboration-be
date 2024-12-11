package com.eventhub.dti.common.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eventhub.dti.common.response.Response;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> handleTransactionSystemException(TransactionSystemException ex) {
        Throwable cause = ex.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Map<String, String> errors = new HashMap<>();
            ConstraintViolationException validationEx = (ConstraintViolationException) cause;
            validationEx.getConstraintViolations().forEach(violation -> {
                String propertyPath = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                errors.put(propertyPath, message);
            });
            log.error("Validation error during transaction: {}", errors);
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
        }
        log.error("Transaction error: ", ex);
        return Response.failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database transaction failed");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException ex) {
        log.error("Database error: {}", ex.getMessage());
        return Response.failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database operation failed");
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmailException(DuplicateEmailException ex) {
        return Response.failedResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(DuplicatePhoneException.class)
    public ResponseEntity<?> handleDuplicatePhoneException(DuplicatePhoneException ex) {
        return Response.failedResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return Response.failedResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(InvalidReferralCodeException.class)
    public ResponseEntity<?> handleInvalidReferralCodeException(InvalidReferralCodeException ex) {
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<?> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return Response.failedResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        log.warn("Authentication error: {}", ex.getMessage());
        String message = ex.getMessage();
        if (message == null || message.isEmpty()) {
            message = "Invalid credentials or token";
        }
        return Response.failedResponse(HttpStatus.UNAUTHORIZED.value(), "Authentication failed: " + message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        return Response.failedResponse(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<?> handleInvalidImageException(InvalidImageException ex) {
        log.error("Invalid image error: {}", ex.getMessage());
        return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception ex) {
        log.error("Unexpected error: ", ex);
        return Response.failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
    }
}
