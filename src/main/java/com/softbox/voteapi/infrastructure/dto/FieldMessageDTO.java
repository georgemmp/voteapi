package com.softbox.voteapi.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FieldMessageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;
}
