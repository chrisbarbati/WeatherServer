package com.chrisbarbati.weatherserver.weather.repository;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;
import com.chrisbarbati.weatherserver.weather.utils.WeatherSampler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.jpa.show-sql=false"
})
public class WeatherSamplingQueryTest {

    private static final long MINUTE_MS = 60_000L;
    private static final long THREE_HOURS_MS = 3 * 60 * 60 * 1000L;
    private static final long THREE_HOURS_SEC = 3 * 3600L;

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    @DisplayName("SQL sampling matches in-memory WeatherSampler and does not return every minute")
    public void sqlSamplingMatchesInMemorySampler() {
        Date newest = new Date();
        int minutes = 3 * 24 * 60;
        List<WeatherEntity> inserted = insertMinuteSeries(minutes, newest);

        Date start = inserted.get(0).getDstamp();
        Date end = newest;
        List<WeatherEntity> sql = weatherRepository.findSampled(start, end, THREE_HOURS_SEC);
        List<WeatherEntity> memory = WeatherSampler.downsample(newestFirst(inserted), THREE_HOURS_MS);

        assertEquals(memory.get(0).getDstamp(), sql.get(0).getDstamp());
        assertTrue(Math.abs(sql.size() - memory.size()) <= 1,
                "SQL " + sql.size() + " vs in-memory " + memory.size());
        assertTrue(sql.size() < minutes / 50, "expected hours of data, not every minute");
    }

    @Test
    @DisplayName("from/to only samples inside the requested window")
    public void dateRangeBoundsTheSample() {
        Date newest = new Date();
        insertMinuteSeries(24 * 60, newest);

        Date from = new Date(newest.getTime() - 6 * 60 * 60 * 1000L);
        List<WeatherEntity> sql = weatherRepository.findSampled(from, newest, THREE_HOURS_SEC);

        assertTrue(sql.size() >= 2 && sql.size() <= 4);
        for (WeatherEntity row : sql) {
            assertTrue(!row.getDstamp().before(from));
            assertTrue(!row.getDstamp().after(newest));
        }
    }

    private List<WeatherEntity> insertMinuteSeries(int count, Date newest) {
        List<WeatherEntity> rows = new ArrayList<>(count);
        for (int i = count - 1; i >= 0; i--) {
            WeatherEntity row = new WeatherEntity();
            row.setTemperature(20.0 + (i % 7) * 0.1);
            row.setHumidity(50.0);
            row.setPressure(1000.0);
            row.setDstamp(new Date(newest.getTime() - (long) i * MINUTE_MS));
            rows.add(row);
        }
        return weatherRepository.saveAll(rows);
    }

    private static List<WeatherEntity> newestFirst(List<WeatherEntity> oldestFirst) {
        List<WeatherEntity> copy = new ArrayList<>(oldestFirst);
        copy.sort((a, b) -> b.getDstamp().compareTo(a.getDstamp()));
        return copy;
    }
}
