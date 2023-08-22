package com.softbox.voteapi.infrastructure.adapter.guideline;

import com.softbox.voteapi.application.useCase.guideline.SaveGuideline;
import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.adapter.guideline.mapper.GuidelineMapper;
import com.softbox.voteapi.infrastructure.api.dto.GuidelineDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SaveGuidelineAdapter {

    private final SaveGuideline saveGuideline;

    public Mono<Guideline> execute(GuidelineDTO dto) {
        Guideline guideline = GuidelineMapper.mapper(dto);

        return this.saveGuideline.execute(guideline);
    }
}
