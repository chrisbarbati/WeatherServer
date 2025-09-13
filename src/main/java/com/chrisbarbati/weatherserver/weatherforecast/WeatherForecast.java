package com.chrisbarbati.weatherserver.weatherforecast;

import java.util.Date;

/**
 * Model class to hold information about the weather forecast
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public class WeatherForecast {
    private Date date;
    private Double baroSlopeHourlyLinear;
    private Double baroSlopeHourlyQuadratic;

    /**
     * Default constructor simply sets the date.
     *
     * The slope values are to be set by the DefaultWeatherForecastBuilder class to prevent this model class containing excess logic
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    public WeatherForecast(){
        date = new Date();
    }

    /**
     * Override the toString method to print a meaningful message
     *
     * @return A human-readable representation of the {@link WeatherForecast} object
     * @since 1.0.0
     * @author Christian Barbati
     */
    public String toString(){
        return "Date: " + date + ", Linear Slope of Baro: " + baroSlopeHourlyLinear + ", Quadratic Slope of Baro: " + baroSlopeHourlyQuadratic;
    }

    /*
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
