package com.chrisbarbati.weatherserver.Models.weather;

import com.chrisbarbati.SenseHAT.Units.PressureUnits;
import com.chrisbarbati.SenseHAT.Units.TempUnits;

import java.util.Date;

public class Weather {
    /**
     * Model class to hold information about the current weather data.
     */
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private Date dstamp;

    private TempUnits tempUnit;
    private PressureUnits pressureUnit;

    /**
     * Default constructor required for Entity class
     */
    public Weather(){

    }

    /**
     * Fully parameterized constructor
     *
     * @param temperature Temperature in the specified units
     * @param humidity % Relative Humidity
     * @param pressure Pressure in the specified units
     * @param tempUnit Units for temperature
     * @param pressureUnit Units for pressure
     */
    public Weather(double temperature, double humidity, double pressure, TempUnits tempUnit, PressureUnits pressureUnit){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.tempUnit = tempUnit;
        this.pressureUnit = pressureUnit;
        this.dstamp = new Date();
    }

    /**
     * Override the toString method
     * @return String representation of the Weather object
     */
    @Override
    public String toString(){
        return "Temperature: " + getTemperature() +
                "Humidity: " + getHumidity() +
                "Pressure: " + getPressure() +
                "Date: " + getDstamp();
    }

    /**
     * Override the equals method
     *
     * @param o Weather object to compare
     * @return True if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        // True if object is compared to itself
        if (this == o) return true;

        // False if object is null, or is not an instance of the Weather class
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        //Note: DSTAMP is not included in the equals method, as it is not a part of the data that is compared
        return this.getTemperature().equals(weather.getTemperature()) &&
                this.getHumidity().equals(weather.getHumidity()) &&
                this.getPressure().equals(weather.getPressure());
    }

    /**
     * Getters and setters
     */

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public TempUnits getTempUnit() {
        return tempUnit;
    }

    public PressureUnits getPressureUnit() {
        return pressureUnit;
    }

    public Date getDstamp() {
        return dstamp;
    }
}
