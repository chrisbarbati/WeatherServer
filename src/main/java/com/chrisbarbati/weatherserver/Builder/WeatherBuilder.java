package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.Models.Weather;

/**
 * Abstract Builder class to create a Weather object.
 */

public interface WeatherBuilder {
    Weather getWeather();
    Weather getWeather(String tempUnitString, String pressureUnitString);
}
