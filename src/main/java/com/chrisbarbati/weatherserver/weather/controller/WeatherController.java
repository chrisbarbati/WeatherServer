package com.chrisbarbati.weatherserver.weather.controller;

import com.chrisbarbati.weatherserver.weather.model.WeatherBuilder;
import com.chrisbarbati.weatherserver.weatherforecast.WeatherForecastBuilder;
import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;
import com.chrisbarbati.weatherserver.weather.model.Weather;
import com.chrisbarbati.weatherserver.weatherforecast.WeatherForecast;
import com.chrisbarbati.weatherserver.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller to handle requests pertaining to weather data
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@CrossOrigin
@RestController
@RequestMapping("/API")
public class WeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    // Injected dependencies
    private WeatherService weatherService;
    private WeatherBuilder weatherBuilder;
    private WeatherForecastBuilder weatherForecastBuilder;

    /**
     * Construct a new instance of {@link WeatherController}.
     * <p>
     *     All parameters injected.
     * </p>
     *
     * @param weatherService
     * @param weatherBuilder
     * @param weatherForecastBuilder
     * @since 1.0.0
     * @author Christian Barbati
     */
    public WeatherController(WeatherService weatherService, WeatherBuilder weatherBuilder, WeatherForecastBuilder weatherForecastBuilder){
        this.weatherService = weatherService;
        this.weatherBuilder = weatherBuilder;
        this.weatherForecastBuilder = weatherForecastBuilder;

        LOGGER.info("WeatherController initialized");
    }

    /**
     * Get the current weather data.
     *
     * @param tempUnit Temperature unit (Celsius, Fahrenheit, or Kelvin). Optional, default Celsius.
     * @param pressureUnit Pressure unit (Millibar, PSI). Optional, default Millibar.
     * @return {@link Weather} object with the current weather data.
     * @since 1.0.0
     * @author Christian Barbati
     */
    @GetMapping("/weather")
    public Weather getWeather(@RequestParam(value = "temp-unit", required = false) String tempUnit, @RequestParam(value = "pressure-unit", required = false) String pressureUnit){

        if(tempUnit != null){
            weatherBuilder.tempUnit(tempUnit);
        }

        if(pressureUnit != null){
            weatherBuilder.pressureUnit(pressureUnit);
        }

        Weather weather = weatherBuilder.build();

        LOGGER.info("Weather data retrieved: " + weather.toString());
        return weather;
    }

    /**
     * Gets all of the past weather records
     *
     * TODO: Add parameters to filter the data
     *
     * @return All of the past weather data
     * @since 1.0.0
     * @author Christian Barbati
     */
    @GetMapping("/weather/past")
    public List<WeatherEntity> getWeatherData(){
        List<WeatherEntity> weatherData = weatherService.getWeatherDataByDateDescending();
        LOGGER.info("Weather data retrieved: " + weatherData.toString());
        return weatherData;
    }

    /**
     * Gets the weather data from the past hour
     *
     * @return Weather data from the past hour
     * @since 1.0.0
     * @author Christian Barbati
     */
    @GetMapping("/weather/pasthour")
    public List<WeatherEntity> getWeatherDataPastHour(){
        List<WeatherEntity> weatherData = weatherService.getWeatherDataLastHour();
        LOGGER.info("Weather data retrieved: " + weatherData.toString());
        return weatherData;
    }


    /**
     * TODO: Experimental. Returns forecast information.
     *
     * @return Weather forecast information
     * @since 1.0.0
     * @author Christian Barbati
     */
    @GetMapping("/weather/forecast")
    public WeatherForecast getWeatherForecast(){
        WeatherForecast weatherForecast = weatherForecastBuilder.getWeatherForecast();
        LOGGER.info("Weather forecast retrieved " + weatherForecast.toString());
        return weatherForecast;
    }

}