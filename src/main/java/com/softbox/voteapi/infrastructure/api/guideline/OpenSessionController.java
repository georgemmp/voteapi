package com.softbox.voteapi.infrastructure.api.guideline;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import com.softbox.voteapi.infrastructure.adapter.guideline.OpenSessionAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/guideline")
@RequiredArgsConstructor
public class OpenSessionController {

    private final OpenSessionAdapter openSessionAdapter;

    @PatchMapping(value = "/{guidelineId}/session")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<Guideline> openSession(@PathVariable String guidelineId) {
        return this.openSessionAdapter.execute(guidelineId);
    }
}
