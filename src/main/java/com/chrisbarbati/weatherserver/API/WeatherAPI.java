package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.Models.weather.WeatherBuilder;
import com.chrisbarbati.weatherserver.Models.weatherForecast.WeatherForecastBuilder;
import com.chrisbarbati.weatherserver.Entities.weather.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.weather.Weather;
import com.chrisbarbati.weatherserver.Models.weatherForecast.WeatherForecast;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API class to handle requests for weather data.
 */

@CrossOrigin
@RestController
@RequestMapping("/API")
public class WeatherAPI {

    private WeatherService weatherService;
    private WeatherBuilder weatherBuilder;
    private WeatherForecastBuilder weatherForecastBuilder;

    private static Logger log = LoggerFactory.getLogger(WeatherAPI.class);

    @Autowired
    public WeatherAPI(WeatherService weatherService, WeatherBuilder weatherBuilder, WeatherForecastBuilder weatherForecastBuilder){
        this.weatherService = weatherService;
        this.weatherBuilder = weatherBuilder;
        this.weatherForecastBuilder = weatherForecastBuilder;

        log.info("WeatherAPI initialized");
    }

    /**
     * Method to get the current weather data.
     * @param tempUnit Temperature unit (Celsius, Fahrenheit, or Kelvin). Optional, default Celsius.
     * @param pressureUnit Pressure unit (Millibar, PSI). Optional, default Millibar.
     * @return Weather object with the current weather data.
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

        log.info("Weather data retrieved: " + weather.toString());
        return weather;
    }

    /**
     * Gets all of the past weather records
     *
     * TODO: Add parameters to filter the data
     *
     * @return Past hour of weather data
     */
    @GetMapping("/weather/past")
    public List<WeatherEntity> getWeatherData(){
        List<WeatherEntity> weatherData = weatherService.getWeatherDataByDateDescending();
        log.info("Weather data retrieved: " + weatherData.toString());
        return weatherData;
    }

    /**
     * Gets the weather data from the past hour
     *
     * @return Weather data from the past hour
     */
    @GetMapping("/weather/pasthour")
    public List<WeatherEntity> getWeatherDataPastHour(){
        List<WeatherEntity> weatherData = weatherService.getWeatherDataLastHour();
        log.info("Weather data retrieved: " + weatherData.toString());
        return weatherData;
    }


    /**
     * TODO: Experimental. Returns forecast information.
     *
     * @return Weather forecast information
     */
    @GetMapping("/weather/forecast")
    public WeatherForecast getWeatherForecast(){
        WeatherForecast weatherForecast = weatherForecastBuilder.getWeatherForecast();
        log.info("Weather forecast retrieved " + weatherForecast.toString());
        return weatherForecast;
    }

}