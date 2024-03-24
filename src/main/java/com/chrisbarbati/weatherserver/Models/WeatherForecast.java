package com.chrisbarbati.weatherserver.Models;

import java.util.Date;

/**
 * Model class to hold information about the weather forecast
 */
public class WeatherForecast {
    private Date date;
    private Double baroSlopeHourlyLinear;
    private Double baroSlopeHourlyQuadratic;

    /**
     * Default constructor simply sets the date.
     *
     * The slope values are set by the WeatherForecastBuilder class to decouple the classes.
     */
    public WeatherForecast(){
        date = new Date();
    }

    public String toString(){
        return "Date: " + date + ", Linear Slope of Baro: " + baroSlopeHourlyLinear + ", Quadratic Slope of Baro: " + baroSlopeHourlyQuadratic;
    }

    /**
     * Getters and setters
     */

    public Double getBaroSlopeHourlyLinear() {
        return baroSlopeHourlyLinear;
    }

    public void setBaroSlopeHourlyLinear(Double baroSlopeHourlyLinear) {
        this.baroSlopeHourlyLinear = baroSlopeHourlyLinear;
    }

    public Double getBaroSlopeHourlyQuadratic() {
        return baroSlopeHourlyQuadratic;
    }

    public void setBaroSlopeHourlyQuadratic(Double baroSlopeHourlyQuadratic) {
        this.baroSlopeHourlyQuadratic = baroSlopeHourlyQuadratic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
