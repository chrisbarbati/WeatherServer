package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.Models.Weather;
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
        //Uses the WeatherBuilder class to a new Weather object with the current weather data
        WeatherBuilder wb = new WeatherBuilder();

        return wb.getWeather(tempUnit, pressureUnit);
    }

}