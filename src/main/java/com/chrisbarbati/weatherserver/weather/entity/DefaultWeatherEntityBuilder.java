package com.chrisbarbati.weatherserver.weather.entity;

import com.chrisbarbati.SenseHAT.Units.PressureUnits;
import com.chrisbarbati.SenseHAT.Units.TempUnits;
import com.chrisbarbati.weatherserver.weather.model.DefaultWeatherBuilder;
import com.chrisbarbati.weatherserver.weather.model.Weather;
import com.chrisbarbati.weatherserver.weather.model.WeatherBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Component to build {@link WeatherEntity}s
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Component
public class DefaultWeatherEntityBuilder implements WeatherEntityBuilder {

    private Double temperature;
    private Double humidity;
    private Double pressure;

    private TempUnits tempUnit;
    private PressureUnits pressureUnit;

    /**
     * Default constructor
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherEntityBuilder() {
    }

    /**
     * Given a Weather object, construct a WeatherEntity object.
     *
     * @param weather An existing {@link Weather} object
     * @return A new {@link WeatherEntity} object
     * @since 1.0.0
     * @author Christian Barbati
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
     * Get a WeatherEntity object.
     * <p>
     *     Delegates to {@link WeatherBuilder}.
     * </p>
     *
     * @return A new {@link WeatherEntity} object
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Override
    public WeatherEntity getWeatherEntity() {
        WeatherBuilder wb = new DefaultWeatherBuilder();

        Weather weather = wb.build();

        return getWeatherEntity(weather);
    }
}
