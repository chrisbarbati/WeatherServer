package com.chrisbarbati.weatherserver.Services;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Repositories.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherRepository weatherRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetWeatherDataByDateDescending() {
        //Create two weather entity objects and store in a list
        WeatherEntity weatherEntity1 = new WeatherEntity();
        WeatherEntity weatherEntity2 = new WeatherEntity();
        List<WeatherEntity> weatherEntities = Arrays.asList(weatherEntity1, weatherEntity2);

        //Mock the findAllByOrderByDstampDesc method to return the list of weather entities
        when(weatherRepository.findAllByOrderByDstampDesc()).thenReturn(weatherEntities);

        //Call the getWeatherDataByDateDescending method
        List<WeatherEntity> result = weatherService.getWeatherDataByDateDescending();

        //Verify that the result is the same as the list of weather entities
        assertEquals(weatherEntities, result);
        //Verify that the findAllByOrderByDstampDesc method was called once
        verify(weatherRepository, times(1)).findAllByOrderByDstampDesc();
    }

    @Test
    public void testGetWeatherDataLastHour() {
        //Create two weather entity objects and store in a list
        WeatherEntity weatherEntity1 = new WeatherEntity();
        WeatherEntity weatherEntity2 = new WeatherEntity();
        List<WeatherEntity> weatherEntities = Arrays.asList(weatherEntity1, weatherEntity2);

        //Create a date object for the current time and one hour ago
        Date currentTime = new Date();
        Date oneHourAgo = new Date(currentTime.getTime() - 3600000);

        //Mock the findByDstampBetweenOrderByDstampDesc method to return the list of weather entities
        //any(Date.class) is used to match any date object, otherwise the test would fail
        when(weatherRepository.findByDstampBetweenOrderByDstampDesc(any(Date.class), any(Date.class))).thenReturn(weatherEntities);

        //Call the getWeatherDataLastHour method
        List<WeatherEntity> result = weatherService.getWeatherDataLastHour();

        //Verify that the result is the same as the list of weather entities
        assertEquals(weatherEntities, result);
        verify(weatherRepository, times(1)).findByDstampBetweenOrderByDstampDesc(any(Date.class), any(Date.class));
    }
}