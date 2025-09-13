package com.chrisbarbati.weatherserver.scheduler;

import com.chrisbarbati.weatherserver.weather.service.DefaultWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Component to handle persisting weather data to the database at set intervals
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Component
public class PersistenceScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceScheduler.class);

    // Injected dependencies
    private final DefaultWeatherService weatherService;

    /**
     * Construct an instance of {@link PersistenceScheduler}.
     * <p>
     *     All parameters injected.
     * </p>
     *
     * @param weatherService
     * @since 1.0.0
     * @author Christian Barbati
     */
    public PersistenceScheduler(DefaultWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * At the specified interval, persist the weather data to the database
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Scheduled(cron = "${scheduling.interval.cron.weather.persistence}")
    public void saveWeatherData() {
        LOGGER.info("Saving weather data to database");

        weatherService.saveWeatherData();
    }
}