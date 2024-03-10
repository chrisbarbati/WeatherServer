package com.chrisbarbati.weatherserver.Services;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.Builder.WeatherBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * Saves current weather data to the database
     *
     * @param weather Weather object to save
     */
    @Transactional
    public void saveWeatherData(Weather weather) {
        WeatherBuilderInterface wb = new WeatherBuilder();

        WeatherEntity weatherEntity = new WeatherEntity(wb.getWeather());

        weatherRepository.save(weatherEntity);
    }

    /**
     * Hibernate ORM will handle the sorting of the data based on the naming convention.
     * @return A list of all WeatherEntity objects sorted by date in descending order.
     */
    public List<WeatherEntity> getWeatherDataByDateDescending(){
        return weatherRepository.findAllByOrderByDstampDesc();
    }
}

