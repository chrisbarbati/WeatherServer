package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;

/**
 * Abstract Builder class to create a WeatherEntity object.
 */

public interface WeatherEntityBuilderInterface {
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
