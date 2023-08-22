package com.softbox.voteapi.infrastructure.adapter.vote.mapper;

import com.softbox.voteapi.domain.entity.vote.Vote;
import com.softbox.voteapi.infrastructure.api.dto.VoteDTO;
import org.springframework.beans.BeanUtils;

public class VoteMapper {

    private VoteMapper() {}

    public static Vote mapper(VoteDTO dto) {
        Vote vote = new Vote();

        BeanUtils.copyProperties(dto, vote);

        return vote;
    }
}
