package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.RPI.SenseHATI2C;
import com.chrisbarbati.weatherserver.Models.Weather;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API class to handle requests for weather data.
 */

@CrossOrigin
@RestController
@RequestMapping("/API")
public class WeatherAPI {

    @GetMapping("/weather")
    public Weather getWeather(){
        //Uses the WeatherBuilder class to a new Weather objcet with the current weather data
        WeatherBuilder wb = new WeatherBuilder();
        return wb.getWeather();
    }
}
