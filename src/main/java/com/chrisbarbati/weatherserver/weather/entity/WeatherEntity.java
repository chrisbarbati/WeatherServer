package com.chrisbarbati.weatherserver.weather.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

/**
 * Entity class to hold information about the current weather data.
 *
 * No longer extends Weather class.
 *
 * TODO: Make this implementation cleaner.
 * @since 1.0.0
 * @author Christian Barbati
 */
@Entity
@Table(name="weather")
public class WeatherEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore //Don't want the ID field returned with the data as it is essentially meaningless to the user.
    @Column(nullable = false, name="id")
    private int id;

    @Column(nullable = false, name="temperature")
    Double temperature;

    @Column(nullable = false, name="humidity")
    Double humidity;

    @Column(nullable = false, name="pressure")
    Double pressure;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name="dstamp")
    Date dstamp;

    /**
     * Default constructor
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    public WeatherEntity(){
    }

    /**
     * Override the toString method
     *
     * @return String representation of the WeatherEntity object
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Override
    public String toString(){
        return "Temperature: " + getTemperature() +
                "Humidity: " + getHumidity() +
                "Pressure: " + getPressure() +
                "Date: " + getDstamp();
    }

    /*
     * Getters and setters
     */
    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDstamp() {
        return dstamp;
    }

    public void setDstamp(Date dstamp) {
        this.dstamp = dstamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }



}
