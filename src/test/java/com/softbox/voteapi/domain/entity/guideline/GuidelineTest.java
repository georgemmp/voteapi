package com.softbox.voteapi.domain.entity.guideline;

import com.softbox.voteapi.domain.entity.guideline.Guideline;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuidelineTest {

    @Test
    public void shouldOpenSession() {
        Guideline guideline = Guideline.builder()
                .session(false)
                .build();

        guideline.openSession();

        assertTrue(guideline.getSession());
    }

    @Test
    public void shouldCloseSession() {
        Guideline guideline = Guideline.builder()
                .session(true)
                .date(LocalDateTime.MIN)
                .build();

        guideline.closeSession();

        assertFalse(guideline.getSession());
    }

    @Test
    public void verifyIfSessionIsOpen() {
        Guideline guideline = Guideline.builder()
                .session(true)
                .build();

        guideline.verifyIfSessionIsOpen();

        assertTrue(guideline.getSession());
    }
}
