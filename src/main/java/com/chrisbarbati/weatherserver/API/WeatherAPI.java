package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.RPI.SenseHat;
import com.chrisbarbati.weatherserver.Models.Weather;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/API")
public class WeatherAPI {

    @GetMapping("/weather")
    public Weather getWeather(){
        //Uses the SenseHAT class to get current weather information, and return the new Weather object as JSON API.
        double temp = SenseHat.getTemperatureAveraged();
        double humidity = SenseHat.getHumidity();
        double pressure = SenseHat.getPressureMbar();
        return new Weather(temp, humidity, pressure);
    }
}
