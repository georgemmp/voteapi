package com.softbox.voteapi.infrastructure.http.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ExceptionError extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;
}
