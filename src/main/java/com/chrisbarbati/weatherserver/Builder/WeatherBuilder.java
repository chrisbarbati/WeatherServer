package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.RPI.SenseHATI2C;

/**
 * Builder class to create a Weather object.
 *
 * Built out as a separate class to make it easier to modify WeatherAPI in the future. Unlikely to be
 * necessary, but it's good practice to separate duties
 */
public class WeatherBuilder {

    public Weather getWeather(){
        //Uses the SenseHAT class to get current weather information
        double temperature = SenseHATI2C.getTempFromPressure();
        double humidity = SenseHATI2C.getHumidity();
        double pressure = SenseHATI2C.getPressureMbar();

        return new Weather(temperature, humidity, pressure);
    }
}
