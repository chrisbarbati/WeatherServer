package com.chrisbarbati.weatherserver.Entities.weather;

import com.chrisbarbati.weatherserver.Models.weather.Weather;

/**
 * Abstract Builder class to create a WeatherEntity object.
 */

public interface WeatherEntityBuilder {

    /**
     * Takes a Weather object and returns a WeatherEntity object
     * @param weather An existing Weather object
     * @return A WeatherEntity object
     */
    WeatherEntity getWeatherEntity(Weather weather);

    /**
     * Get a WeatherEntity object
     * @return A WeatherEntity object
     */
    WeatherEntity getWeatherEntity();
}
