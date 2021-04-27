package com.example.matchmaker.boundary.exception;

import com.example.matchmaker.boundary.api.impl.MatchmakerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.StringJoiner;

@Slf4j
@ControllerAdvice(assignableTypes = MatchmakerController.class)
public class MatchmakerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> defaultExceptionHandler(Exception ex) {
        final var message = String.format("Internal server error: [%s]", ex.getMessage());
        log.error(message);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        final var joiner = new StringJoiner("],[", "Validation failed: [", "]");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            joiner.add(fieldName + " : " + errorMessage);
        });
        final var message = joiner.toString();
        log.error(message);
        return new ResponseEntity<>(message, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        final var message = "Received malformed json request";
        log.error(message);
        return new ResponseEntity<>(message, status);
    }
}
