package com.example.petoasisbackend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName;
            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
            } else {
                fieldName = "general";
            }
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleJsonParseErrors(HttpMessageNotReadableException e) {
        Map<String, String> errors = new HashMap<>();
        Throwable cause = e.getCause();

        if (cause != null && cause.getMessage() != null) {
            String msg = cause.getMessage();

            if (msg.contains("Unknown gender")) {
                errors.put("gender", "must be one of: M, F, U");
            } else if (msg.contains("Cannot deserialize value of type `java.time.LocalDate`")) {
                errors.put("birthDate", "must be in format yyyy-MM-dd");
            } else if (msg.contains("Walk") && msg.contains("Request") && msg.contains("endTime")) {
                errors.put("endTime", "must be in zulu time format");
            } else if (msg.contains("Walk") && msg.contains("Request") && msg.contains("startTime")) {
                errors.put("startTime", "must be in zulu time format");
            } else {
                System.out.println(msg);
                errors.put("json", "Invalid JSON format");
            }
        } else {
            errors.put("json", "Malformed JSON or invalid data format");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errors);
    }
}
