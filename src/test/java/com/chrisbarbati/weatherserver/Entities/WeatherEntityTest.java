package com.chrisbarbati.weatherserver.Entities;

import com.chrisbarbati.weatherserver.Entities.weather.WeatherEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherEntityTest {

    private WeatherEntity weatherEntity;

    @BeforeEach
    public void init() {
        weatherEntity = new WeatherEntity();
        weatherEntity.setTemperature(20.0);
        weatherEntity.setHumidity(50.0);
        weatherEntity.setPressure(1013.0);
        weatherEntity.setDstamp(new Date());
    }

    @Test
    @DisplayName("Should return correct temperature")
    public void shouldReturnCorrectTemperature() {
        assertEquals(20.0, weatherEntity.getTemperature());
    }

    @Test
    @DisplayName("Should return correct humidity")
    public void shouldReturnCorrectHumidity() {
        assertEquals(50.0, weatherEntity.getHumidity());
    }

    @Test
    @DisplayName("Should return correct pressure")
    public void shouldReturnCorrectPressure() {
        assertEquals(1013.0, weatherEntity.getPressure());
    }

    @Test
    @DisplayName("Should return correct date stamp")
    public void shouldReturnCorrectDateStamp() {
        Date date = new Date();
        weatherEntity.setDstamp(date);
        assertEquals(date, weatherEntity.getDstamp());
    }

    @Test
    @DisplayName("Should return correct string representation")
    public void shouldReturnCorrectStringRepresentation() {
        String expected = "Temperature: " + weatherEntity.getTemperature() +
                "Humidity: " + weatherEntity.getHumidity() +
                "Pressure: " + weatherEntity.getPressure() +
                "Date: " + weatherEntity.getDstamp();
        assertEquals(expected, weatherEntity.toString());
    }
}