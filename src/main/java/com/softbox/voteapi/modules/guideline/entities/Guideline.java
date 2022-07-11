package com.softbox.voteapi.modules.guideline.entities;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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
    private String result;
    private LocalDateTime date;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
