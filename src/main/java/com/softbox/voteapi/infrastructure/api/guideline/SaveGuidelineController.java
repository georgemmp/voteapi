package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.adapter.guideline.SaveGuidelineAdapter;
import com.softbox.voteapi.infrastructure.api.dto.GuidelineDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/guideline")
@RequiredArgsConstructor
public class SaveGuidelineController {

    private final SaveGuidelineAdapter saveGuidelineAdapter;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Guideline> save(@RequestBody GuidelineDTO guidelineDTO) {
        return this.saveGuidelineAdapter.execute(guidelineDTO);
    }
}
