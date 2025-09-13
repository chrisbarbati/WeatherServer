package com.chrisbarbati.weatherserver.weather.model;

import com.chrisbarbati.SenseHAT.SenseHAT;
import com.chrisbarbati.SenseHAT.Units.PressureUnits;
import com.chrisbarbati.SenseHAT.Units.TempUnits;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Component to build {@link Weather} objects
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Component
public class DefaultWeatherBuilder implements WeatherBuilder {

    //Instance variables
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private Date dstamp;

    private TempUnits tempUnit;
    private PressureUnits pressureUnit;

    /**
     * Default constructor
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherBuilder() {
    }

    /**
     * Set the TempUnit for the Weather to be constructed
     *
     * @param tempUnit - TempUnits enum to set the temperature unit
     * @return this
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherBuilder tempUnit(TempUnits tempUnit){
        this.tempUnit = tempUnit;

        return this;
    }

    /**
     * Set the PressureUnit for the Weather to be constructed
     *
     * @param pressureUnit - PressureUnits enum to set the pressure unit
     * @return this
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherBuilder pressureUnit(PressureUnits pressureUnit){
        this.pressureUnit = pressureUnit;

        return this;
    }

    /**
     * Set the temperature unit for the Weather to be constructed
     *
     * @param tempUnitString - String representation of the TempUnits enum
     * @return this
     * @throws IllegalArgumentException - If the string is null or invalid
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherBuilder tempUnit(String tempUnitString) throws IllegalArgumentException {

        if(tempUnitString == null) {
            throw new IllegalArgumentException("Cannot set temperature unit to null");
        }

        //Convert the string to a TempUnits enum - throws IllegalArgumentException if invalid
        tempUnit = TempUnits.valueOf(tempUnitString.toUpperCase());

        return this;
    }

    /**
     * Set the pressure unit for the Weather to be constructed
     *
     * @param pressureUnitString - String representation of the PressureUnits enum
     * @return this
     * @throws IllegalArgumentException - If the string is null or invalid
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherBuilder pressureUnit(String pressureUnitString){

        if(pressureUnitString == null) {
            throw new IllegalArgumentException("Cannot set pressure unit to null");
        }

        //Convert the string to a PressureUnits enum - throws IllegalArgumentException if invalid
        pressureUnit = PressureUnits.valueOf(pressureUnitString.toUpperCase());

        return this;
    }

    /**
     * Query the SenseHAT sensors to get the current temperature, humidity, and pressure and set the instance variables
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    private void querySensors(){

        if(tempUnit == null){
            tempUnit = TempUnits.CELSIUS;
        }

        if(pressureUnit == null){
            pressureUnit = PressureUnits.MILLIBAR;
        }

        SenseHAT sh = new SenseHAT();

        temperature = sh.getTempFromHumidity(tempUnit);
        humidity = sh.getHumidity();
        pressure = sh.getPressure(pressureUnit);

    }

    /**
     * Build the Weather object
     *
     * @return {@link Weather} object with the current weather data
     * @since 1.0.0
     * @author Christian Barbati
     */
    public Weather build(){

        if(tempUnit == null){
            tempUnit = TempUnits.CELSIUS;
        }

        if(pressureUnit == null){
            pressureUnit = PressureUnits.MILLIBAR;
        }

        querySensors();

        dstamp = new Date();

        return new Weather(temperature, humidity, pressure, tempUnit, pressureUnit);
    }

}
