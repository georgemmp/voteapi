package com.softbox.voteapi.modules.guideline.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GuidelineDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Required field")
    private String description;
}