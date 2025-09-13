package com.chrisbarbati.weatherserver.weatherforecast;

import com.chrisbarbati.weatherserver.weather.utils.WeatherUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component to build {@link WeatherForecast} objects
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Component
public class DefaultWeatherForecastBuilder implements WeatherForecastBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWeatherForecastBuilder.class);

    // Injected dependencies
    private final WeatherUtils weatherUtils;

    /**
     * Cosntruct an instance of {@link DefaultWeatherForecastBuilder}.
     * <p>
     *     All parameters injected.
     * </p>
     *
     * @param weatherUtils
     * @since 1.0.0
     * @author Christian Barbati
     */
    public DefaultWeatherForecastBuilder(WeatherUtils weatherUtils){
        this.weatherUtils = weatherUtils;
    }

    /**
     * Construct a {@link WeatherForecast}
     *
     * @return {@link WeatherForecast}
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Override
    public WeatherForecast getWeatherForecast() {
        WeatherForecast weatherForecast = new WeatherForecast();

        weatherForecast.setBaroSlopeHourlyLinear(weatherUtils.getBaroSlopeHourlyLinear());
        weatherForecast.setBaroSlopeHourlyQuadratic(weatherUtils.getBaroSlopeHourlyPolynomial());

        LOGGER.info("Weather forecast retrieved: {}", weatherForecast);

        return weatherForecast;
    }


}
