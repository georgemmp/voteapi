package com.softbox.voteapi.infrastructure.adapter.vote.mapper;

import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.infrastructure.api.dto.VoteDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VoteMapperTest {

    @Test
    public void shouldTransferDtoDataToModel() {
        VoteDTO dto = VoteDTO.builder()
                .associateCpf("123456")
                .voteDescription("test")
                .build();

        Vote vote = VoteMapper.mapper(dto);

        assertNotNull(vote);
        assertEquals("123456", vote.getAssociateCpf());
        assertEquals("test", vote.getVoteDescription());
    }
}
