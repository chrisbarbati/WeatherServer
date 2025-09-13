package com.chrisbarbati.weatherserver.weather.model;

import com.chrisbarbati.SenseHAT.Units.PressureUnits;
import com.chrisbarbati.SenseHAT.Units.TempUnits;

/**
 * Builder for {@link Weather} objects
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public interface WeatherBuilder {

    /**
     * Set the temperature unit for the Weather to be constructed
     *
     * @param tempUnitString - String representation of the TempUnits enum
     * @return this
     * @throws IllegalArgumentException - If the string is null or invalid
     * @since 1.0.0
     * @author Christian Barbati
     */
    WeatherBuilder tempUnit(String tempUnitString);
    
    /**
     * Set the pressure unit for the Weather to be constructed
     *
     * @param pressureUnitString - String representation of the PressureUnits enum
     * @return this
     * @throws IllegalArgumentException - If the string is null or invalid
     * @since 1.0.0
     * @author Christian Barbati
     */
    WeatherBuilder pressureUnit(String pressureUnitString);

    /**
     * Set the TempUnit for the Weather to be constructed
     *
     * @param tempUnit - TempUnits enum to set the temperature unit
     * @return this
     * @since 1.0.0
     * @author Christian Barbati
     */
    WeatherBuilder tempUnit(TempUnits tempUnit);

    /**
     * Set the PressureUnit for the Weather to be constructed
     *
     * @param pressureUnit - PressureUnits enum to set the pressure unit
     * @return this
     * @since 1.0.0
     * @author Christian Barbati
     */
    WeatherBuilder pressureUnit(PressureUnits pressureUnit);

    /**
     * Build the Weather object
     *
     * @return {@link Weather} object with the current weather data
     * @since 1.0.0
     * @author Christian Barbati
     */
    Weather build();
}
