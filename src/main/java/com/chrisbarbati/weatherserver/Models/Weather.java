package com.chrisbarbati.weatherserver.Models;

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

    public Weather(double temperature, double humidity, double pressure){
        setTemperature(temperature);
        setHumidity(humidity);
        setPressure(pressure);
        setDstamp(new Date());
    }

    public Weather(double temperature, double humidity, double pressure, TempUnits tempUnit, PressureUnits pressureUnit){
        setTemperature(temperature);
        setHumidity(humidity);
        setPressure(pressure);
        setTempUnit(tempUnit);
        setPressureUnit(pressureUnit);
        setDstamp(new Date());
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public TempUnits getTempUnit() {
        return tempUnit;
    }

    public void setTempUnit(TempUnits tempUnit) {
        this.tempUnit = tempUnit;
    }

    public PressureUnits getPressureUnit() {
        return pressureUnit;
    }

    public void setPressureUnit(PressureUnits pressureUnit) {
        this.pressureUnit = pressureUnit;
    }

    public Date getDstamp() {
        return dstamp;
    }

    public void setDstamp(Date dstamp) {
        this.dstamp = dstamp;
    }
}
