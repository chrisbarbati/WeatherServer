package com.chrisbarbati.weatherserver.Models;

public class Weather {
    /**
     * Model class to hold information about the current weather data.
     */
    private double temperature;
    private double humidity;
    private double pressure;

    private TempUnits tempUnit;
    private PressureUnits pressureUnit;

    public Weather(double temperature, double humidity, double pressure){
        setTemperature(temperature);
        setHumidity(humidity);
        setPressure(pressure);
    }

    public Weather(double temperature, double humidity, double pressure, TempUnits tempUnit, PressureUnits pressureUnit){
        setTemperature(temperature);
        setHumidity(humidity);
        setPressure(pressure);
        setTempUnit(tempUnit);
        setPressureUnit(pressureUnit);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
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
}
