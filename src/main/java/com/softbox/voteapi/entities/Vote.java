package com.softbox.voteapi.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    private Long voteId;
    private String voteDescription;
    private Guideline guideline;
    private Associate associate;
}
