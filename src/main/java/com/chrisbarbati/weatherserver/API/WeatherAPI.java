package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.Builder.WeatherBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * API class to handle requests for weather data.
 */

@CrossOrigin
@RestController
@RequestMapping("/API")
public class WeatherAPI {

    private WeatherService weatherService;

    @Autowired
    public WeatherAPI(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    /**
     * Method to get the current weather data.
     * @param tempUnit Temperature unit (Celsius, Fahrenheit, or Kelvin). Optional, default Celsius.
     * @param pressureUnit Pressure unit (Millibar, PSI). Optional, default Millibar.
     * @return Weather object with the current weather data.
     */
    @GetMapping("/weather")
    public Weather getWeather(@RequestParam(value = "temp-unit", required = false) String tempUnit, @RequestParam(value = "pressure-unit", required = false) String pressureUnit){
        //Uses the WeatherBuilderInterface to get the weather data from the SenseHAT.
        //Uses the interface for dependency inversion
        WeatherBuilderInterface wb = new WeatherBuilder();

        return wb.getWeather(tempUnit, pressureUnit);
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

}