package com.softbox.voteapi.domain.entity.cpfValidator;

import com.softbox.voteapi.domain.type.StatusCpfVote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CpfValidatorResponseTest {

    @Test
    public void checkIfCpfIsUnableToVote() {
        CpfValidatorResponse response = CpfValidatorResponse.builder()
                .status(StatusCpfVote.UNABLE_TO_VOTE)
                .build();

        Assertions.assertTrue(response.checkCpf());
    }
}
