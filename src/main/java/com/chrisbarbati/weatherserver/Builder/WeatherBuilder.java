package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.SenseHAT.*;
import com.chrisbarbati.SenseHAT.Units.*;
import com.chrisbarbati.weatherserver.Models.Weather;
import org.springframework.stereotype.Component;

/**
 * Builder class to create a Weather object.
 *
 * Built out as a separate class to make it easier to modify WeatherAPI in the future. Unlikely to be
 * necessary, but it's good practice to separate duties
 *
 * Annotated @Component so it can be injected
 */

@Component
public class WeatherBuilder implements WeatherBuilderInterface {

    /**
     * Get temperature, humidity, and pressure from the SenseHAT
     *
     * Parameterless method defaults to celsius and millibar
     */
    @Override
    public Weather getWeather(){
        SenseHAT sh = new SenseHAT();
        double temperature = sh.getTempFromHumidity(TempUnits.CELSIUS);
        double humidity = sh.getHumidity();
        double pressure = sh.getPressure(PressureUnits.MILLIBAR);

        return new Weather(temperature, humidity, pressure);
    }

/**
     * Get temperature, humidity, and pressure from the SenseHAT
     *
     * @param tempUnitString String representing the temperature unit to use
     * @param pressureUnitString String representing the pressure unit to use
     */
    @Override
    public Weather getWeather(String tempUnitString, String pressureUnitString){
        SenseHAT hat = new SenseHAT();

        double temperature;
        double humidity;
        double pressure;

        TempUnits tempUnit;
        PressureUnits pressureUnit;

        /**
         * Get temperature, dependent on units. If no unit is specified, assume celsius
         *
         * Perform null test first to avoid null pointer exception
         *
         * Might be better handled with handler methods for larger projects where extensibility is required,
         * but for this situation it is not necessary as I don't expect to add more units.
         */
        if(tempUnitString == null){
            tempUnit = TempUnits.CELSIUS;
        }else{
            if(tempUnitString.equals("celsius") || tempUnitString.equals("")) {
                tempUnit = TempUnits.CELSIUS;
            } else if(tempUnitString.equals("fahrenheit")){
                tempUnit = TempUnits.FAHRENHEIT;
            } else if(tempUnitString.equals("kelvin")){
                tempUnit = TempUnits.KELVIN;
            }
            else {
                throw new IllegalArgumentException("Invalid temperature unit");
            }
        }

        /**
         * Get pressure, dependent on units. If no unit is specified, assume millibar
         *
         * Perform null test first to avoid null pointer exception
         */
        if(pressureUnitString == null) {
            pressureUnit = PressureUnits.MILLIBAR;
        }else{
            pressureUnitString = pressureUnitString.trim().toLowerCase();

            if(pressureUnitString.equals("millibar") || pressureUnitString.equals("")) {
                pressureUnit = PressureUnits.MILLIBAR;
            } else if(pressureUnitString.equals("psi")){
                pressureUnit = PressureUnits.PSI;
            } else {
                throw new IllegalArgumentException("Invalid pressure unit");
            }
        }

        //Get temperature and pressure dependent on the units selected.
        temperature = hat.getTempFromPressure(tempUnit);
        pressure = hat.getPressure(pressureUnit);
        //Get humidity, as % of relative humidity
        humidity = hat.getHumidity();

        return new Weather(temperature, humidity, pressure, tempUnit, pressureUnit);
    }
}
