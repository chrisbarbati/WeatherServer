package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.weatherforecast.DefaultWeatherForecastBuilder;
import com.chrisbarbati.weatherserver.weatherforecast.WeatherForecast;
import com.chrisbarbati.weatherserver.weather.utils.WeatherUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DefaultWeatherForecastBuilderTest {

    @InjectMocks
    private DefaultWeatherForecastBuilder weatherForecastBuilder;

    @Mock
    private WeatherUtils weatherUtils;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Verify that the WeatherForecast object is created with the correct barometric pressure slopes
     */
    @Test
    @DisplayName("Should return WeatherForecast with correct barometric pressure slopes")
    public void shouldReturnWeatherForecastWithCorrectBarometricPressureSlopes() {
        when(weatherUtils.getBaroSlopeHourlyLinear()).thenReturn(0.02);
        when(weatherUtils.getBaroSlopeHourlyPolynomial()).thenReturn(0.03);

        WeatherForecast result = weatherForecastBuilder.getWeatherForecast();

        assertEquals(0.02, result.getBaroSlopeHourlyLinear());
        assertEquals(0.03, result.getBaroSlopeHourlyQuadratic());
    }
}