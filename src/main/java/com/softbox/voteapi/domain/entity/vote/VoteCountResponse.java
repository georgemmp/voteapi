package com.softbox.voteapi.domain.entity.vote;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VoteCountResponse {

    private Vote vote;

    private Long yes;

    private Long no;

    private String guidelineId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
