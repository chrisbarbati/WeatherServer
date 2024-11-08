package com.chrisbarbati.weatherserver.Utils;

import com.chrisbarbati.weatherserver.Entities.weather.WeatherEntity;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WeatherUtilsTest {

    @InjectMocks
    private WeatherUtils weatherUtils;

    @Mock
    private WeatherService weatherService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should return correct barometric pressure slope for linear regression")
    public void shouldReturnCorrectBaroSlopeHourlyLinear() {
        WeatherEntity weatherEntity1 = new WeatherEntity();
        weatherEntity1.setPressure(1000.0);
        WeatherEntity weatherEntity2 = new WeatherEntity();
        weatherEntity2.setPressure(1010.0);
        when(weatherService.getWeatherDataLastHour()).thenReturn(Arrays.asList(weatherEntity1, weatherEntity2));

        Double result = weatherUtils.getBaroSlopeHourlyLinear();

        assertEquals(10.0, result);
    }

    @Test
    @DisplayName("Should return correct barometric pressure slope for polynomial regression")
    public void shouldReturnCorrectBaroSlopeHourlyPolynomial() {
        WeatherEntity weatherEntity1 = new WeatherEntity();
        weatherEntity1.setPressure(1000.0);
        WeatherEntity weatherEntity2 = new WeatherEntity();
        weatherEntity2.setPressure(1010.0);
        when(weatherService.getWeatherDataLastHour()).thenReturn(Arrays.asList(weatherEntity1, weatherEntity2));

        Double result = weatherUtils.getBaroSlopeHourlyPolynomial();

        assertEquals(10.0, result);
    }
}