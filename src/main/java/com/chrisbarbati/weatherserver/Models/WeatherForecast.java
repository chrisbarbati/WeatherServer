package com.chrisbarbati.weatherserver.Models;

import com.chrisbarbati.weatherserver.Utils.WeatherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
