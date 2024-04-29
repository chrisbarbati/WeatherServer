package com.chrisbarbati.weatherserver.Services;

import com.chrisbarbati.weatherserver.Builder.WeatherEntityBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Repositories.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Service class to handle the WeatherRepository and WeatherEntity objects
 *
 * Provides methods to save and retrieve weather data that will be
 * called by the API controller
 */

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final WeatherEntityBuilderInterface weatherEntityBuilder;
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, WeatherEntityBuilderInterface weatherEntityBuilder) {
        this.weatherRepository = weatherRepository;
        this.weatherEntityBuilder = weatherEntityBuilder;

        log.info("WeatherService created");
    }

    /**
     * Saves current weather data to the database
     */
    @Transactional
    @CacheEvict(value = {"weatherDataByDateDescending", "weatherDataLastHour"}, allEntries = true)
    public void saveWeatherData() {
        WeatherEntity weatherEntity = weatherEntityBuilder.getWeatherEntity();

        weatherRepository.save(weatherEntity);

        log.info("Saving weather data: " + weatherEntity.toString());
    }

    /**
     * Hibernate ORM will handle the sorting of the data based on the naming convention.
     *
     * Implements caching to reduce the number of calls to the database. Unlikely
     * to matter for my demonstration purposes, but could be useful in a production
     * application where the data is queried more frequently.
     *
     * @return A list of all WeatherEntity objects sorted by date in descending order.
     */
    @Cacheable("weatherDataByDateDescending")
    public List<WeatherEntity> getWeatherDataByDateDescending(){
        List<WeatherEntity> weatherData = weatherRepository.findAllByOrderByDstampDesc();
        log.info("Weather data retrieved: " + weatherData.size() + " records");
        return weatherData;
    }

    /**
     * Gets the last hour of weather data.
     *
     * Implements caching to reduce the number of calls to the database. Unlikely
     * to matter for my demonstration purposes, but could be useful in a production
     * application where the data is queried more frequently.
     *
     * @return A list of WeatherEntity objects from the last hour
     */
    @Cacheable("weatherDataLastHour")
    public List<WeatherEntity> getWeatherDataLastHour(){
        Date currentTime = new Date();
        Date oneHourAgo = new Date(currentTime.getTime() - 3600000);

        List<WeatherEntity> weatherData = weatherRepository.findByDstampBetweenOrderByDstampDesc(oneHourAgo, currentTime);

        log.info("Weather data from the last hour retrieved: " + weatherData.size() + " records");

        return weatherData;
    }
}

