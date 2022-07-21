package com.softbox.voteapi.webClient.dto;

import com.softbox.voteapi.shared.enums.StatusCpfVote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CpfValidatorResponse {
    private StatusCpfVote status;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
