package com.softbox.voteapi.modules.associate.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Associate {
    @Id
    private String associateId;
    private String cpf;
    private String name;
}
