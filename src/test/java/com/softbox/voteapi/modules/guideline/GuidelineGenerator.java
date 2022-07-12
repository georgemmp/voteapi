package com.softbox.voteapi.modules.guideline;

import com.softbox.voteapi.modules.guideline.entities.Guideline;

import java.time.LocalDateTime;

public class GuidelineGenerator {

    public static Guideline.GuidelineBuilder create() {
        return Guideline.builder()
                .guidelineId("123")
                .description("Test")
                .session(false);
    }
}
