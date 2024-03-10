package com.chrisbarbati.weatherserver.Scheduled;

import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class that handles the scheduling of weather data.
 *
 */

@Component
public class WeatherDataScheduler {
    private final WeatherService weatherService;

    private static final Logger log = LoggerFactory.getLogger(WeatherDataScheduler.class);

    @Autowired
    public WeatherDataScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Scheduled(cron = "0 */10 * * * *") // Run every 10 minutes on the minute
    public void saveWeatherData() {
        log.info("Saving weather data to database");

        weatherService.saveWeatherData(new Weather());
    }
}