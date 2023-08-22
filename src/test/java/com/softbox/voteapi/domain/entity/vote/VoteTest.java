package com.softbox.voteapi.domain.entity.vote;

import com.softbox.voteapi.domain.type.VoteDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VoteTest {

    @Test
    public void shouldValidateVote() {
        Vote vote = Vote.builder()
                .guidelineId("x")
                .associateCpf("123456")
                .build();

        Boolean validation = vote.validVote(vote.getAssociateCpf(), vote.getGuidelineId());

        Assertions.assertTrue(validation);
    }

    @Test
    public void shouldValidateVoteDescription() {
        VoteDescription voteDescription = VoteDescription.YES;

        Vote vote = Vote.builder()
                .voteDescription(voteDescription.getDescription())
                .build();

        Boolean validation = vote.equalsVoteDescription(voteDescription);

        Assertions.assertTrue(validation);
    }
}
