package com.softbox.voteapi.infrastructure.adapter.guideline.mapper;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.api.dto.GuidelineDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuidelineMapperTest {

    @Test
    public void shouldTransferDtoDataToModel() {
        GuidelineDTO dto = GuidelineDTO.builder()
                .description("Test")
                .build();

        Guideline guideline = GuidelineMapper.mapper(dto);

        assertNotNull(guideline);
        assertEquals("Test", guideline.getDescription());
    }
}
