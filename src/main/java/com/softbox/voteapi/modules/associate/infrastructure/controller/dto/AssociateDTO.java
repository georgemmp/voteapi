package com.softbox.voteapi.modules.associate.infrastructure.controller.dto;

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
public class AssociateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "CPF is a required field")
    @CPF(message = "Invalid CPF")
    private String cpf;

    @NotEmpty(message = "Name is required field")
    private String name;
}