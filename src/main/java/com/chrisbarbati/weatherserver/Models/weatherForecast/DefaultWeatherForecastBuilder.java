package com.chrisbarbati.weatherserver.Models.weatherForecast;

import com.chrisbarbati.weatherserver.Utils.WeatherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Builder class to create a WeatherForecast object.
 */
@Component
public class DefaultWeatherForecastBuilder implements WeatherForecastBuilder {
    private final WeatherUtils weatherUtils;

    @Autowired
    public DefaultWeatherForecastBuilder(WeatherUtils weatherUtils){
        this.weatherUtils = weatherUtils;
    }

    @Override
    public WeatherForecast getWeatherForecast() {
        WeatherForecast weatherForecast = new WeatherForecast();

        weatherForecast.setBaroSlopeHourlyLinear(weatherUtils.getBaroSlopeHourlyLinear());
        weatherForecast.setBaroSlopeHourlyQuadratic(weatherUtils.getBaroSlopeHourlyPolynomial());

        return weatherForecast;
    }


}
