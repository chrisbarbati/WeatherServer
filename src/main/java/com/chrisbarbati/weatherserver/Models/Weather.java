package com.chrisbarbati.weatherserver.Models;

public class Weather {
    /**
     * Model class to hold information about the current weather.
     */

    private double temperature;
    private double humidity;
    private double pressure;

    public Weather(double temperature, double humidity, double pressure){
        setTemperature(temperature);
        setHumidity(humidity);
        setPressure(pressure);
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
}
