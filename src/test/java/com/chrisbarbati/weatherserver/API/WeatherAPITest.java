package com.chrisbarbati.weatherserver.API;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilderInterface;
import com.chrisbarbati.weatherserver.Builder.WeatherForecastBuilderInterface;
import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Models.WeatherForecast;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WeatherAPITest {

    @InjectMocks
    private WeatherAPI weatherAPI;

    @Mock
    private WeatherService weatherService;

    @Mock
    private WeatherBuilderInterface weatherBuilder;

    @Mock
    private WeatherForecastBuilderInterface weatherForecastBuilder;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test to verify that the getWeather method returns a Weather object
     */
    @Test
    @DisplayName("Should return current weather data")
    public void shouldReturnCurrentWeatherData() {
        Weather weather = new Weather();
        when(weatherBuilder.getWeather(anyString(), anyString())).thenReturn(weather);

        Weather result = weatherAPI.getWeather("Celsius", "Millibar");

        assertEquals(weather, result);
        verify(weatherBuilder, times(1)).getWeather("Celsius", "Millibar");
    }

    /**
     * Test to verify that the getWeatherData method returns past Weather records
     */
    @Test
    @DisplayName("Should return past weather records")
    public void shouldReturnPastWeatherRecords() {
        WeatherEntity weatherEntity1 = new WeatherEntity();
        WeatherEntity weatherEntity2 = new WeatherEntity();
        List<WeatherEntity> weatherEntities = Arrays.asList(weatherEntity1, weatherEntity2);

        when(weatherService.getWeatherDataByDateDescending()).thenReturn(weatherEntities);

        List<WeatherEntity> result = weatherAPI.getWeatherData();

        assertEquals(weatherEntities, result);
        verify(weatherService, times(1)).getWeatherDataByDateDescending();
    }

    /**
     * Test to verify that the getWeatherForecast method returns a WeatherForecast object
     */
    @Test
    @DisplayName("Should return weather forecast")
    public void shouldReturnWeatherForecast() {
        WeatherForecast weatherForecast = new WeatherForecast();
        when(weatherForecastBuilder.getWeatherForecast()).thenReturn(weatherForecast);

        WeatherForecast result = weatherAPI.getWeatherForecast();

        assertEquals(weatherForecast, result);
        verify(weatherForecastBuilder, times(1)).getWeatherForecast();
    }
}