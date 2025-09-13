package com.chrisbarbati.weatherserver.weather.service;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;

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

}
