package com.chrisbarbati.weatherserver.weather.utils;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherSamplerTest {

    private static final long MINUTE_MS = 60_000L;
    private static final long THREE_HOURS_MS = 3 * 60 * 60 * 1000L;

    @Test
    @DisplayName("Three-hour buckets keep one sample per window")
    public void downsamplesMinuteDataToThreeHourBuckets() {
        int days = 10;
        int minutes = days * 24 * 60;
        Date end = new Date();
        List<WeatherEntity> minuteRows = minuteSeries(minutes, end);

        List<WeatherEntity> sampled = WeatherSampler.downsample(minuteRows, THREE_HOURS_MS);

        long newest = end.getTime();
        long oldest = newest - (long) (minutes - 1) * MINUTE_MS;
        int expectedBuckets = (int) (Math.floorDiv(newest, THREE_HOURS_MS) - Math.floorDiv(oldest, THREE_HOURS_MS) + 1);
        assertEquals(expectedBuckets, sampled.size());
        assertEquals(minuteRows.get(0).getDstamp(), sampled.get(0).getDstamp());
    }

    @Test
    @DisplayName("bucketMs <= 0 leaves the series unchanged")
    public void rawDumpWhenBucketDisabled() {
        List<WeatherEntity> rows = minuteSeries(12, new Date());
        assertEquals(rows, WeatherSampler.downsample(rows, 0));
        assertEquals(rows, WeatherSampler.downsample(rows, -1));
    }

    @Test
    @DisplayName("Sampled JSON is a small fraction of a full-minute dump")
    public void sampledJsonIsMuchSmallerThanFullDump() throws Exception {
        int minutes = 90 * 24 * 60;
        List<WeatherEntity> minuteRows = minuteSeries(minutes, new Date());
        List<WeatherEntity> sampled = WeatherSampler.downsample(minuteRows, THREE_HOURS_MS);

        ObjectMapper mapper = new ObjectMapper();
        int fullBytes = mapper.writeValueAsBytes(minuteRows).length;
        int sampledBytes = mapper.writeValueAsBytes(sampled).length;

        assertTrue(sampled.size() < minuteRows.size() / 100, "expected roughly 180x fewer points");
        assertTrue(sampledBytes * 20 < fullBytes,
                "sampled JSON " + sampledBytes + "B should be far smaller than " + fullBytes + "B");
        System.out.println("Proof: " + minuteRows.size() + " minute rows -> " + sampled.size()
                + " three-hour points; JSON " + fullBytes + "B -> " + sampledBytes + "B");
    }

    private static List<WeatherEntity> minuteSeries(int count, Date newest) {
        List<WeatherEntity> rows = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            WeatherEntity row = new WeatherEntity();
            row.setTemperature(20.0 + (i % 7) * 0.1);
            row.setHumidity(50.0);
            row.setPressure(1000.0);
            row.setDstamp(new Date(newest.getTime() - (long) i * MINUTE_MS));
            rows.add(row);
        }
        return rows;
    }
}
