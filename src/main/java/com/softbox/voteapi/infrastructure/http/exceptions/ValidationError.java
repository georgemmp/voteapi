package com.softbox.voteapi.infrastructure.http.exceptions;

import com.softbox.voteapi.infrastructure.dto.FieldMessageDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {

    private static final long serialVersionUID = 1L;

    private List<FieldMessageDTO> errors = new ArrayList<>();

    public ValidationError(Long timestamp, Integer status, String message, String path) {
        super(timestamp, status, message, path);
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessageDTO(fieldName, message));
    }
}
