package com.chrisbarbati.weatherserver.service;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;
import com.chrisbarbati.weatherserver.weather.repository.WeatherRepository;
import com.chrisbarbati.weatherserver.weather.service.DefaultWeatherService;
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
    private DefaultWeatherService weatherService;

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

    @Test
    public void testGetWeatherDataSampledKeepsOneRowPerBucket() {
        Date now = new Date();
        WeatherEntity newest = new WeatherEntity();
        newest.setDstamp(now);
        newest.setTemperature(21.0);
        WeatherEntity sameBucket = new WeatherEntity();
        sameBucket.setDstamp(new Date(now.getTime() - 30 * 60 * 1000L));
        sameBucket.setTemperature(20.5);
        WeatherEntity olderBucket = new WeatherEntity();
        olderBucket.setDstamp(new Date(now.getTime() - 4 * 60 * 60 * 1000L));
        olderBucket.setTemperature(19.0);
        List<WeatherEntity> weatherEntities = Arrays.asList(newest, sameBucket, olderBucket);

        when(weatherRepository.findSampled(any(Date.class), any(Date.class), eq(3 * 3600L))).thenReturn(
                Arrays.asList(newest, olderBucket));

        List<WeatherEntity> result = weatherService.getWeatherDataSampled(3, null, null);

        assertEquals(2, result.size());
        assertEquals(newest, result.get(0));
        assertEquals(olderBucket, result.get(1));
        verify(weatherRepository, times(1)).findSampled(any(Date.class), any(Date.class), eq(3 * 3600L));
        verify(weatherRepository, never()).findAllByOrderByDstampDesc();
    }

    @Test
    public void testGetWeatherDataSampledRawDump() {
        WeatherEntity weatherEntity1 = new WeatherEntity();
        weatherEntity1.setDstamp(new Date());
        List<WeatherEntity> weatherEntities = Arrays.asList(weatherEntity1);
        when(weatherRepository.findAllByOrderByDstampDesc()).thenReturn(weatherEntities);

        assertEquals(weatherEntities, weatherService.getWeatherDataSampled(0, null, null));
        verify(weatherRepository, times(1)).findAllByOrderByDstampDesc();
    }

    @Test
    public void testGetWeatherDataSampledRawRangeDoesNotLoadFullTable() {
        WeatherEntity weatherEntity1 = new WeatherEntity();
        weatherEntity1.setDstamp(new Date());
        List<WeatherEntity> weatherEntities = Arrays.asList(weatherEntity1);
        Date from = new Date(1_700_000_000_000L);
        Date to = new Date(1_700_003_600_000L);
        when(weatherRepository.findByDstampBetweenOrderByDstampDesc(from, to)).thenReturn(weatherEntities);

        assertEquals(weatherEntities, weatherService.getWeatherDataSampled(0, from, to));
        verify(weatherRepository, times(1)).findByDstampBetweenOrderByDstampDesc(from, to);
        verify(weatherRepository, never()).findAllByOrderByDstampDesc();
    }
}
