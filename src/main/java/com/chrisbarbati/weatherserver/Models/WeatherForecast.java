package com.chrisbarbati.weatherserver.Models;

import com.chrisbarbati.weatherserver.Utils.WeatherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Model class to hold information about the weather forecast
 */
@Component
public class WeatherForecast {
    private static WeatherUtils weatherUtils;
    private Date date;

    @Autowired
    public WeatherForecast(WeatherUtils weatherUtils){
        this.weatherUtils = weatherUtils;

    }

    public WeatherForecast(){
        date = new Date();
        baroSlopeHourlyLinear = weatherUtils.getBaroSlopeHourlyLinear();
        baroSlopeHourlyQuadratic = weatherUtils.getBaroSlopeHourlyPolynomial();
    }

    //Experimental - Using both methods for now to see which I prefer
    private Double baroSlopeHourlyLinear;
    private Double baroSlopeHourlyQuadratic;

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
