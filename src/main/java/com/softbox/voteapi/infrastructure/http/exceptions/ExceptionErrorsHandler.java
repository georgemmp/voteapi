package com.softbox.voteapi.infrastructure.http.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerRequest;

@ControllerAdvice
public class ExceptionErrorsHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> internalServerErrorException(RuntimeException error, ServerRequest request) {
        StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getMessage(), request.requestPath().value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError);
    }

    @ExceptionHandler(ExceptionError.class)
    public ResponseEntity<StandardError> exceptionError(ExceptionError error, ServerRequest request) {
        StandardError standardError = new StandardError(System.currentTimeMillis(), error.getHttpStatus().value(), error.getMessage(), request.requestPath().value());
        return ResponseEntity.status(error.getHttpStatus()).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException error, ServerRequest request) {
        ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), error.getMessage(), request.requestPath().value());

        for (FieldError x: error.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }
}
