package com.chrisbarbati.weatherserver.weather.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PastQueryBoundsTest {

    @Test
    @DisplayName("No params means all history")
    public void unboundedWhenNothingSet() {
        PastQueryBounds.Range range = PastQueryBounds.resolve(null, null, null);
        assertNull(range.from);
        assertNull(range.to);
    }

    @Test
    @DisplayName("days=90 is the last 90 days")
    public void daysLooksBackFromNow() {
        long before = System.currentTimeMillis();
        PastQueryBounds.Range range = PastQueryBounds.resolve(90, null, null);
        long after = System.currentTimeMillis();

        assertNotNull(range.from);
        assertNotNull(range.to);
        assertTrue(range.to.getTime() >= before && range.to.getTime() <= after);
        long span = range.to.getTime() - range.from.getTime();
        assertEquals(90L * 24 * 60 * 60 * 1000, span);
    }

    @Test
    @DisplayName("from/to are calendar dates, not timestamps")
    public void calendarDatesCoverTheWholeDays() {
        PastQueryBounds.Range range = PastQueryBounds.resolve(
                90,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2));

        assertTrue(range.from.getTime() < range.to.getTime());
        long span = range.to.getTime() - range.from.getTime();
        assertTrue(span > 47L * 60 * 60 * 1000 && span < 49L * 60 * 60 * 1000);
    }
}
