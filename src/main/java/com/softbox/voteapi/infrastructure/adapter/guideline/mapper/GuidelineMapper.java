package com.softbox.voteapi.infrastructure.adapter.guideline.mapper;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.api.dto.GuidelineDTO;
import org.springframework.beans.BeanUtils;

public class GuidelineMapper {

    private GuidelineMapper() {}

    public static Guideline mapper(GuidelineDTO dto) {
        Guideline guideline = new Guideline();

        BeanUtils.copyProperties(dto, guideline);

        return guideline;
    }
}
