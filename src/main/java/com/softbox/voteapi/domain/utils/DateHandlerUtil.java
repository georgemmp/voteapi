package com.softbox.voteapi.domain.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateHandlerUtil {
    public static long getDiffMinutes (LocalDateTime date, LocalDateTime now) {
        return ChronoUnit.MINUTES.between(date, now);
    }
}
