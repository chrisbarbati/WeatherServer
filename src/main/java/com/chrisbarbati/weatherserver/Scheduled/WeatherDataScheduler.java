package com.chrisbarbati.weatherserver.Scheduled;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataScheduler {
    private final WeatherService weatherService;

    private static final Logger log = LoggerFactory.getLogger(WeatherDataScheduler.class);

    @Autowired
    public WeatherDataScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Scheduled(fixedRate = 600000) //Take a sample every 10 minutes and save to database
    public void saveWeatherData() {
        log.info("Saving weather data to database");

        weatherService.saveWeatherData(new Weather());
    }
}