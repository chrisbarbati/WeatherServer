package com.chrisbarbati.weatherserver.Services;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.Builder.WeatherBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Repositories.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to handle the weather data.
 */

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Transactional
    public void saveWeatherData(Weather weather) {
        WeatherBuilderInterface wb = new WeatherBuilder();

        WeatherEntity weatherEntity = new WeatherEntity(wb.getWeather());

        weatherRepository.save(weatherEntity);
    }

    public List<WeatherEntity> getWeatherData(){
        return weatherRepository.findAll();
    }
}

