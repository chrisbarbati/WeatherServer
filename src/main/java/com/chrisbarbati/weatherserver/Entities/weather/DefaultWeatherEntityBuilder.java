package com.chrisbarbati.weatherserver.Entities.weather;

import com.chrisbarbati.SenseHAT.Units.PressureUnits;
import com.chrisbarbati.SenseHAT.Units.TempUnits;
import com.chrisbarbati.weatherserver.Models.weather.DefaultWeatherBuilder;
import com.chrisbarbati.weatherserver.Models.weather.Weather;
import com.chrisbarbati.weatherserver.Models.weather.WeatherBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Abstract Builder class to create a WeatherEntity object.
 *
 * Annotated as a Component to allow for dependency injection.
 */

@Component
public class DefaultWeatherEntityBuilder implements WeatherEntityBuilder {

    private Double temperature;
    private Double humidity;
    private Double pressure;
    private Date dstamp;

    private TempUnits tempUnit;
    private PressureUnits pressureUnit;

    /**
     * Default constructor
     */
    public DefaultWeatherEntityBuilder() {
    }




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
     * Uses DefaultWeatherBuilder to uncouple this class from the Weather class.
     *
     * @return A new WeatherEntity object
     */
    @Override
    public WeatherEntity getWeatherEntity() {
        WeatherBuilder wb = new DefaultWeatherBuilder();

        Weather weather = wb.build();

        return getWeatherEntity(weather);
    }
}
