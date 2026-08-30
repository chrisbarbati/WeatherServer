package com.chrisbarbati.weatherserver.weather.service;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;

import java.util.Date;
import java.util.List;

/**
 * Service class to handle operations on {@link WeatherEntity} objects
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public interface WeatherService {

    /**
     * Saves current weather data to the database
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    void saveWeatherData();

    /**
     * Get all weather data, sorted by date in descending order
     *
     * @return A {@link List} of all {@link WeatherEntity} objects sorted by date in descending order.
     * @since 1.0.0
     * @author Christian Barbati
     */
    List<WeatherEntity> getWeatherDataByDateDescending();

    /**
     * Gets the last hour of weather data.
     *
     * @return A {@link List} of all {@link WeatherEntity}  objects from the last hour
     * @since 1.0.0
     * @author Christian Barbati
     */
    List<WeatherEntity> getWeatherDataLastHour();

    /**
     * Get past weather data, optionally reduced to one sample per time bucket.
     * Sampling is done in the database. Optional {@code from}/{@code to} bound the query.
     *
     * @param bucketHours width of each bucket in hours. {@code 0} or less returns every stored row.
     * @param from inclusive start, or {@code null} for unbounded
     * @param to inclusive end, or {@code null} for now
     * @return A {@link List} of {@link WeatherEntity} objects, newest first.
     * @since 1.1.0
     * @author Daniel Yevtushenko
     */
    List<WeatherEntity> getWeatherDataSampled(int bucketHours, Date from, Date to);

    /**
     * Same as {@link #getWeatherDataSampled(int, Date, Date)} with no date bounds.
     *
     * @param bucketHours width of each bucket in hours. {@code 0} or less returns every stored row.
     * @return A {@link List} of {@link WeatherEntity} objects, newest first.
     * @since 1.1.0
     * @author Daniel Yevtushenko
     */
    default List<WeatherEntity> getWeatherDataSampled(int bucketHours) {
        return getWeatherDataSampled(bucketHours, null, null);
    }

}
