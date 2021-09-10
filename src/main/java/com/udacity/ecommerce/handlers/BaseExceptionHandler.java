package com.udacity.ecommerce.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
                    String message;
                    if (errors.containsKey(error.getField())) {
                        message = String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage());
                    } else {
                        message = error.getDefaultMessage();
                    }
                    errors.put(error.getField(), message);
                    log.error(message, ex);
                }
        );
        return new ResponseEntity<>(
                new HashMap<String, Object>() {{
                    put("VALIDATION_ERRORS", errors);
                }}, HttpStatus.BAD_REQUEST);
    }
}