package com.softbox.voteapi.domain.entity.vote;

import com.softbox.voteapi.domain.type.VoteDescription;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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

    public boolean validVote(String cpf, String guidelineId) {
        return (this.associateCpf.equals(cpf) &&
                this.guidelineId.equals(guidelineId));
    }

    public boolean equalsVoteDescription(VoteDescription voteDescription) {
        return Objects.equals(this.voteDescription, voteDescription.getDescription());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
