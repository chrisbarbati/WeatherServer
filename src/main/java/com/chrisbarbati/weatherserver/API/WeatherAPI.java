package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.Builder.WeatherBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * API class to handle requests for weather data.
 */

@CrossOrigin
@RestController
@RequestMapping("/API")
public class WeatherAPI {

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam(value = "temp-unit", required = false) String tempUnit, @RequestParam(value = "pressure-unit", required = false) String pressureUnit){
        //Uses the WeatherBuilderInterface to get the weather data from the SenseHAT.
        //Uses the interface for dependency inversion
        WeatherBuilderInterface wb = new WeatherBuilder();

        return wb.getWeather(tempUnit, pressureUnit);
    }

}