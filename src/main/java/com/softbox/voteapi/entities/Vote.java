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
    private String voteId;
    private String voteDescription;
    private String guidelineId;
    private String associateCpf;
}
