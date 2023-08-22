package com.softbox.voteapi.domain.entity.cpfValidator;

import com.softbox.voteapi.domain.type.StatusCpfVote;
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

    public Boolean checkCpf() {
        return this.status == StatusCpfVote.UNABLE_TO_VOTE;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
