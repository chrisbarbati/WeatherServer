package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;

import java.util.Date;

/**
 * Abstract Builder class to create a WeatherEntity object.
 */

public class WeatherEntityBuilder implements WeatherEntityBuilderInterface{

    /**
     * Takes a Weather object and returns a WeatherEntity object.
     *
     * @param weather An existing Weather object
     * @return A new WeatherEntity object
     */
    @Override
    public WeatherEntity getWeatherEntity(Weather weather) {
        WeatherEntity weatherEntity = new WeatherEntity();

        //Set the temperature, humidity, and pressure from the Weather object
        weatherEntity.setTemperature(weather.getTemperature());
        weatherEntity.setHumidity(weather.getHumidity());
        weatherEntity.setPressure(weather.getPressure());

        //Set the datestamp to the current date
        weatherEntity.setDstamp(new Date());

        return weatherEntity;
    }

    /**
     * Get a WeatherEntity object without an existing Weather object.
     *
     * Instantiates a new Weather object and calls the other getWeatherEntity method.
     *
     * Uses WeatherBuilder to uncouple this class from the Weather class.
     *
     * @return A new WeatherEntity object
     */
    @Override
    public WeatherEntity getWeatherEntity() {
        WeatherBuilder wb = new WeatherBuilder();

        Weather weather = wb.getWeather();

        return getWeatherEntity(weather);
    }
}
