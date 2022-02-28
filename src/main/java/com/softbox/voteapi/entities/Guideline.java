package com.softbox.voteapi.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guideline {
    @Id
    private String guidelineId;
    private String description;
    private Boolean session;
    private List<Vote> votes;
}
