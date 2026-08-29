package com.chrisbarbati.weatherserver.weather.utils;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Downsamples a weather series by keeping one reading per time bucket.
 * <p>
 * When the source list is newest-first (as {@code findAllByOrderByDstampDesc} returns),
 * the first row seen for a bucket is the latest sample in that window.
 * </p>
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public final class WeatherSampler {

    private WeatherSampler() {
    }

    /**
     * Reduce {@code rows} to at most one {@link WeatherEntity} per {@code bucketMs} window.
     *
     * @param rows     weather rows, typically newest first
     * @param bucketMs bucket width in milliseconds. Values {@code <= 0} return {@code rows} unchanged.
     * @return a new list, still newest-first
     */
    public static List<WeatherEntity> downsample(List<WeatherEntity> rows, long bucketMs) {
        if (rows == null || rows.isEmpty() || bucketMs <= 0) {
            return rows;
        }

        Map<Long, WeatherEntity> buckets = new LinkedHashMap<>();
        for (WeatherEntity row : rows) {
            if (row == null || row.getDstamp() == null) {
                continue;
            }
            long key = Math.floorDiv(row.getDstamp().getTime(), bucketMs);
            buckets.putIfAbsent(key, row);
        }
        return new ArrayList<>(buckets.values());
    }
}
