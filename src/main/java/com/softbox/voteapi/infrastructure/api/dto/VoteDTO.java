package com.softbox.voteapi.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "CPF is required")
    @CPF(message = "Invalid CPF")
    String associateCpf;

    @NotEmpty(message = "Vote Description is required")
    String voteDescription;
}
