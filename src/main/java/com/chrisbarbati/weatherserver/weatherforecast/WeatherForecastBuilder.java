package com.chrisbarbati.weatherserver.weatherforecast;

/**
 * Builder {@link WeatherForecast} objects
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public interface WeatherForecastBuilder {

    /**
     * Construct a {@link WeatherForecast}
     *
     * @return {@link WeatherForecast}
     * @since 1.0.0
     * @author Christian Barbati
     */
    WeatherForecast getWeatherForecast();

}
