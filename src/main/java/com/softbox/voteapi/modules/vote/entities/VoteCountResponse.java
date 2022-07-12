package com.softbox.voteapi.modules.vote.entities;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteCountResponse {
    private Long yes;
    private Long no;
    private String guidelineId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
