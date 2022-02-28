package com.softbox.voteapi.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Associate {
    @Id
    private String associateId;
    private String cpf;
    private String name;
    private List<Vote> vote;
}
