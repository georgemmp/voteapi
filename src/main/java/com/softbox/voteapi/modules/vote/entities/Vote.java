package com.softbox.voteapi.modules.vote.entities;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
