package com.chrisbarbati.weatherserver.weather.repository;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;

import java.util.Date;
import java.util.List;

/**
 * Native sampling queries so {@code /API/weather/past} does not have to load
 * the entire {@code weather} table into memory.
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public interface WeatherRepositoryCustom {

    /**
     * One row per time bucket, newest row in each window (highest {@code id}).
     *
     * @param start inclusive lower bound
     * @param end   inclusive upper bound
     * @param bucketSeconds width of each bucket in seconds
     * @return newest-first sampled rows
     */
    List<WeatherEntity> findSampled(Date start, Date end, long bucketSeconds);
}
