package com.chrisbarbati.weatherserver.Services;

import com.chrisbarbati.weatherserver.Builder.WeatherEntityBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public WeatherService(WeatherRepository weatherRepository, WeatherEntityBuilderInterface weatherEntityBuilder) {
        this.weatherRepository = weatherRepository;
        this.weatherEntityBuilder = weatherEntityBuilder;
    }

    /**
     * Saves current weather data to the database
     *
     * @param weather Weather object to save
     */
    @Transactional
    public void saveWeatherData(Weather weather) {
        weatherRepository.save(weatherEntityBuilder.getWeatherEntity());
    }

    /**
     * Hibernate ORM will handle the sorting of the data based on the naming convention.
     * @return A list of all WeatherEntity objects sorted by date in descending order.
     */
    public List<WeatherEntity> getWeatherDataByDateDescending(){
        return weatherRepository.findAllByOrderByDstampDesc();
    }

    /**
     * Gets the last hour of weather data
     * @return A list of WeatherEntity objects from the last hour
     */
    public List<WeatherEntity> getWeatherDataLastHour(){
        Date currentTime = new Date();
        Date oneHourAgo = new Date(currentTime.getTime() - 3600000);
        return weatherRepository.findByDstampBetweenOrderByDstampDesc(oneHourAgo, currentTime);
    }
}

