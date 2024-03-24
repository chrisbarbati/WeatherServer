package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import com.chrisbarbati.weatherserver.Models.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherEntityBuilderTest {

    @InjectMocks
    private WeatherEntityBuilder weatherEntityBuilder;

    @Mock
    private WeatherBuilder weatherBuilder;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Methods that create a new WeatherEntity object will inherently fail as I am developing on a Windows machine.
     *
     * See WeatherBuilderTest for more details.
     */

    /**
     * Verify that converting a Weather object to a WeatherEntity object works as expected
     */
    @Test
    @DisplayName("Should return WeatherEntity from Weather object")
    public void shouldReturnWeatherEntityFromWeatherObject() {
        Weather weather = new Weather();
        weather.setTemperature(20.0);
        weather.setHumidity(50.0);
        weather.setPressure(1013.0);

        WeatherEntity result = weatherEntityBuilder.getWeatherEntity(weather);

        assertEquals(weather.getTemperature(), result.getTemperature());
        assertEquals(weather.getHumidity(), result.getHumidity());
        assertEquals(weather.getPressure(), result.getPressure());
    }
}