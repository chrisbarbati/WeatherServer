package com.chrisbarbati.weatherserver.Scheduled;

import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler class.
 *
 * Any task that must run at a specific time or interval can be scheduled here.
 */

@Component
public class WeatherDataScheduler {
    private final WeatherService weatherService;

    private static final Logger log = LoggerFactory.getLogger(WeatherDataScheduler.class);

    @Autowired
    public WeatherDataScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Save weather data to the database every 10 minutes.
     *
     * cron = ... represents making entries at ten minute intervals,
     * starting at the top of the hour
     */
    @Scheduled(cron = "0 */1 * * * *") // Run every 1 minute, on the minute
    public void saveWeatherData() {
        log.info("Saving weather data to database");

        weatherService.saveWeatherData();
    }
}