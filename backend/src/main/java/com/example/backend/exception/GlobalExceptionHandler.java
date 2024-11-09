package com.example.backend.exception;

import com.example.backend.dto.ValidationErrorResponse;
import com.example.backend.util.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> errorDetails = new HashMap<>();

            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errorDetails.put("field", StringUtils.camelToSnake(field));
            errorDetails.put("message", message);

            errors.add(errorDetails);
        });

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", "Validation Failed", LocalDateTime.now(), errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if(ex.getMessage() != null && ex.getMessage().contains("account_email_key")) {
            return new ResponseEntity<>(Map.of("message", "the email is already in use. please choose a different one"), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(Map.of("message", "An error occurred while processing your request."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

