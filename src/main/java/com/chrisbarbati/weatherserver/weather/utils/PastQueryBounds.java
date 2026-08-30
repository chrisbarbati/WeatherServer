package com.chrisbarbati.weatherserver.weather.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Turns the simple {@code /past} query params into a start/end for SQL.
 * <p>
 *     Prefer {@code days=90} or calendar dates ({@code from=2024-01-01}) over
 *     full timestamps.
 * </p>
 *
 * @since 1.1.0
 * @author Daniel Yevtushenko
 */
public final class PastQueryBounds {

    public static final class Range {
        public final Date from;
        public final Date to;

        public Range(Date from, Date to) {
            this.from = from;
            this.to = to;
        }
    }

    private PastQueryBounds() {
    }

    /**
     * {@code from}/{@code to} win if either is set. Otherwise {@code days} means
     * the last N days. If nothing is set, both bounds are {@code null} (all history).
     */
    public static Range resolve(Integer days, LocalDate from, LocalDate to) {
        ZoneId zone = ZoneId.systemDefault();
        if (from != null || to != null) {
            Date start = from == null ? new Date(0L) : Date.from(from.atStartOfDay(zone).toInstant());
            Date end = to == null ? new Date() : Date.from(to.plusDays(1).atStartOfDay(zone).toInstant().minusMillis(1));
            return new Range(start, end);
        }
        if (days != null && days > 0) {
            Date end = new Date();
            return new Range(new Date(end.getTime() - days * 24L * 60L * 60L * 1000L), end);
        }
        return new Range(null, null);
    }
}
