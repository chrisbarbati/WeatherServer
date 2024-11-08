package com.chrisbarbati.weatherserver.Models.weather;

import com.chrisbarbati.SenseHAT.Units.PressureUnits;
import com.chrisbarbati.SenseHAT.Units.TempUnits;

/**
 * Interface for Builder classes to create a Weather object.
 */

public interface WeatherBuilder {

    Weather build();

    WeatherBuilder tempUnit(String tempUnitString);
    WeatherBuilder pressureUnit(String pressureUnitString);

    WeatherBuilder tempUnit(TempUnits tempUnit);
    WeatherBuilder pressureUnit(PressureUnits pressureUnit);

}
