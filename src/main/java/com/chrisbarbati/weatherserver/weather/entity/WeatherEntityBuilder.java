package com.chrisbarbati.weatherserver.weather.entity;

import com.chrisbarbati.weatherserver.weather.model.Weather;

/**
 * Builder for {@link WeatherEntity}s
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public interface WeatherEntityBuilder {

    /**
     * Given a Weather object, construct a WeatherEntity object.
     *
     * @param weather An existing {@link Weather} object
     * @return A new {@link WeatherEntity} object
     * @since 1.0.0
     * @author Christian Barbati
     */
    WeatherEntity getWeatherEntity(Weather weather);

    /**
     * Get a WeatherEntity object
     *
     * @return A new {@link WeatherEntity} object
     * @since 1.0.0
     * @author Christian Barbati
     */
    WeatherEntity getWeatherEntity();
}
