package com.chrisbarbati.weatherserver.Utils;

import com.chrisbarbati.weatherserver.Entities.weather.WeatherEntity;
import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class to hold utility methods for weather data.
 */
@Component
public class WeatherUtils {
    private WeatherService weatherService;

    private static final Logger log = LoggerFactory.getLogger(WeatherUtils.class);

    @Autowired
    public WeatherUtils(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    /**
     * Method to calculate the slope of the barometric pressure over the last hour.
     *
     * Performs a simple linear regression on the barometric pressure data over the last hour,
     * then gets the slope of the line
     *
     * @return The slope of the barometric pressure over the last hour.
     */
    public Double getBaroSlopeHourlyLinear(){
        List<WeatherEntity> hourlyData = weatherService.getWeatherDataLastHour();

        log.debug("Hourly data size: " + hourlyData.size());

        //Uses the Apache Commons Math library
        SimpleRegression regression = new SimpleRegression();

        for(int i = 0; i < hourlyData.size(); i++){
            regression.addData(i, hourlyData.get(i).getPressure());
        }

        Double slope = regression.getSlope();

        return slope;
    }

    /**
     * Gets the slope of the barometric pressure over the last hour using a polynomial regression.
     *
     * TODO: Experimental. Unclear if this will provide any advantage over the linear regression, other
     * than being a bit more extensible because I can get the second derivative of the quadratic function
     * to find the rate of change of the slope.
     *
     * @return The slope of the barometric pressure over the last hour.
     */
    public Double getBaroSlopeHourlyPolynomial(){
        List<WeatherEntity> hourlyData = weatherService.getWeatherDataLastHour();

        log.debug("Hourly data size: " + hourlyData.size());

        //Uses the Apache Commons Math library
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2); //Assumes a quadratic function, will test to see if this is a good assumption

        WeightedObservedPoints points = new WeightedObservedPoints();

        for(int i = 0; i < hourlyData.size(); i++){
            points.add(i, hourlyData.get(i).getPressure());
        }

        double [] termCoefficients = fitter.fit(points.toList());

        double xSquared = termCoefficients[2];
        double x = termCoefficients[1];

        /**
            Get the slope of the line tangent to the curve at the last point. First derivative is y' = 2ax + b,
            where y' is the slope at x.

            In this case A is xSquared, B is x, so y' = (2 * xSquared * x) + b,
         */
        Double slope = (2 * xSquared * hourlyData.size()) + x;

        return slope;
    }


}
