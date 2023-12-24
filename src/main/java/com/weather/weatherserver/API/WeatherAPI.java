package com.weather.weatherserver.API;

import com.weather.weatherserver.Models.Weather;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API")
public class WeatherAPI {
    @GetMapping("/weather")
    public Weather getWeather(){
        //Example information. Connect this to the library I created later.
        return new Weather(25.0, 1005, 33.42);
    }
}
