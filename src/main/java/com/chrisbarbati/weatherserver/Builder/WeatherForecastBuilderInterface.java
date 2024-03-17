package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.Models.WeatherForecast;

/**
 * Interface for the WeatherForecastBuilder class
 */
public interface WeatherForecastBuilderInterface {
    /**
     * Get a WeatherForecast object
     * @return A WeatherForecast object
     */
    WeatherForecast getWeatherForecast();
}
