package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Models.WeatherForecast;
import com.chrisbarbati.weatherserver.Services.WeatherService;
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
    private WeatherBuilderInterface weatherBuilder;

    @Autowired
    public WeatherAPI(WeatherService weatherService, WeatherBuilderInterface weatherBuilder){
        this.weatherService = weatherService;
        this.weatherBuilder = weatherBuilder;
    }

    /**
     * Method to get the current weather data.
     * @param tempUnit Temperature unit (Celsius, Fahrenheit, or Kelvin). Optional, default Celsius.
     * @param pressureUnit Pressure unit (Millibar, PSI). Optional, default Millibar.
     * @return Weather object with the current weather data.
     */
    @GetMapping("/weather")
    public Weather getWeather(@RequestParam(value = "temp-unit", required = false) String tempUnit, @RequestParam(value = "pressure-unit", required = false) String pressureUnit){
        return weatherBuilder.getWeather(tempUnit, pressureUnit);
    }

    /**
     * Gets all of the past weather records
     *
     * @return Past weather records, sorted by date in descending order.
     */
    @GetMapping("/weather/past")
    public List<WeatherEntity> getWeatherData(){
        return weatherService.getWeatherDataByDateDescending();
    }

    /**
     * TODO: Experimental. Returns forecast information.
     *
     * @return Weather forecast information
     */
    @GetMapping("/weather/forecast")
    public WeatherForecast getWeatherForecast(){
        return new WeatherForecast();
    }

}