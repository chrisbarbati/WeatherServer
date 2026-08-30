package com.chrisbarbati.weatherserver.weather.service;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntityBuilder;
import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;
import com.chrisbarbati.weatherserver.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service class to handle operations on {@link WeatherEntity} objects
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Service
public class DefaultWeatherService implements WeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWeatherService.class);

    // Injected dependencies
    private final WeatherRepository weatherRepository;
    private final WeatherEntityBuilder weatherEntityBuilder;

    /**
     * Construct a new instance of {@link DefaultWeatherService}.
     * <p>
     *     All parameters injected.
     * </p>
     * @param weatherRepository
     * @param weatherEntityBuilder
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherService(WeatherRepository weatherRepository, WeatherEntityBuilder weatherEntityBuilder) {
        this.weatherRepository = weatherRepository;
        this.weatherEntityBuilder = weatherEntityBuilder;

        LOGGER.info("WeatherService created");
    }

    /**
     * Saves current weather data to the database
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Transactional
    @CacheEvict(value = {"weatherDataByDateDescending", "weatherDataLastHour", "weatherDataSampled"}, allEntries = true)
    public void saveWeatherData() {
        WeatherEntity weatherEntity = weatherEntityBuilder.getWeatherEntity();

        weatherRepository.save(weatherEntity);

        LOGGER.info("Saving weather data: " + weatherEntity.toString());
    }

    /**
     * Get all weather data, sorted by date in descending order
     *
     * Implements caching to reduce the number of calls to the database. Unlikely
     * to matter for my demonstration purposes, but could be useful in a production
     * application where the data is queried more frequently.
     *
     * @return A {@link List} of all {@link WeatherEntity} objects sorted by date in descending order.
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Cacheable("weatherDataByDateDescending")
    public List<WeatherEntity> getWeatherDataByDateDescending(){
        List<WeatherEntity> weatherData = weatherRepository.findAllByOrderByDstampDesc();
        LOGGER.info("Weather data retrieved: " + weatherData.size() + " records");
        return weatherData;
    }

    /**
     * Gets the last hour of weather data.
     *
     * Implements caching to reduce the number of calls to the database. Unlikely
     * to matter for my demonstration purposes, but could be useful in a production
     * application where the data is queried more frequently.
     *
     * @return A {@link List} of all {@link WeatherEntity}  objects from the last hour
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Cacheable("weatherDataLastHour")
    public List<WeatherEntity> getWeatherDataLastHour(){
        Date currentTime = new Date();
        Date oneHourAgo = new Date(currentTime.getTime() - 3600000);

        List<WeatherEntity> weatherData = weatherRepository.findByDstampBetweenOrderByDstampDesc(oneHourAgo, currentTime);

        LOGGER.info("Weather data from the last hour retrieved: " + weatherData.size() + " records");

        return weatherData;
    }

    /**
     * Past weather, reduced to one sample per {@code bucketHours} window.
     * <p>
     *     Sampling happens in SQL so the Pi does not load the entire table into
     *     memory. Optional {@code from}/{@code to} bounds use the same query.
     *     Pass {@code bucketHours <= 0} for every stored row in that range.
     * </p>
     *
     * @param bucketHours bucket width in hours
     * @param from inclusive start, or {@code null} for the oldest row
     * @param to inclusive end, or {@code null} for now
     * @return newest-first sampled (or raw) history
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Override
    @Cacheable(value = "weatherDataSampled", key = "#bucketHours + '-' + #from + '-' + #to")
    public List<WeatherEntity> getWeatherDataSampled(int bucketHours, Date from, Date to) {
        Date start = from != null ? from : new Date(0L);
        Date end = to != null ? to : new Date();
        if (start.after(end)) {
            return Collections.emptyList();
        }

        if (bucketHours <= 0) {
            if (from == null && to == null) {
                return getWeatherDataByDateDescending();
            }
            List<WeatherEntity> range = weatherRepository.findByDstampBetweenOrderByDstampDesc(start, end);
            LOGGER.info("Weather data retrieved: " + range.size() + " records (raw range)");
            return range;
        }

        List<WeatherEntity> sampled = weatherRepository.findSampled(start, end, bucketHours * 3600L);
        LOGGER.info("Weather data sampled to " + sampled.size()
                + " records (bucket-hours=" + bucketHours + ")");
        return sampled;
    }
}

