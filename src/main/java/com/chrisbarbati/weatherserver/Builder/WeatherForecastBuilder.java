package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.Models.WeatherForecast;
import com.chrisbarbati.weatherserver.Utils.WeatherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Interface to define the methods for the WeatherEntityBuilder class
 */
@Component
public class WeatherForecastBuilder implements WeatherForecastBuilderInterface{
    private final WeatherUtils weatherUtils;

    @Autowired
    public WeatherForecastBuilder(WeatherUtils weatherUtils){
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
