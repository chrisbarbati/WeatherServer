package com.chrisbarbati.weatherserver.model;

import com.chrisbarbati.SenseHAT.Units.PressureUnits;
import com.chrisbarbati.SenseHAT.Units.TempUnits;
import com.chrisbarbati.weatherserver.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherTest {

    private Weather weather;

    @BeforeEach
    public void init() {
        weather = new Weather(20.0, 50.0, 1013.0, TempUnits.CELSIUS, PressureUnits.MILLIBAR);
    }

    @Test
    @DisplayName("Should return correct temperature")
    public void shouldReturnCorrectTemperature() {
        assertEquals(20.0, weather.getTemperature());
    }

    @Test
    @DisplayName("Should return correct humidity")
    public void shouldReturnCorrectHumidity() {
        assertEquals(50.0, weather.getHumidity());
    }

    @Test
    @DisplayName("Should return correct pressure")
    public void shouldReturnCorrectPressure() {
        assertEquals(1013.0, weather.getPressure());
    }

    @Test
    @DisplayName("Should return correct temperature unit")
    public void shouldReturnCorrectTemperatureUnit() {
        assertEquals(TempUnits.CELSIUS, weather.getTempUnit());
    }

    @Test
    @DisplayName("Should return correct pressure unit")
    public void shouldReturnCorrectPressureUnit() {
        assertEquals(PressureUnits.MILLIBAR, weather.getPressureUnit());
    }

    //TODO: Update this test to work with the new implementation
//    @Test
//    @DisplayName("Should return correct date stamp")
//    public void shouldReturnCorrectDateStamp() {
//        Date date = new Date();
//        weather.setDstamp(date);
//        assertEquals(date, weather.getDstamp());
//    }
}