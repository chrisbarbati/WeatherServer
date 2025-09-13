package com.chrisbarbati.weatherserver.scheduler;

import com.chrisbarbati.weatherserver.weather.model.Weather;
import com.chrisbarbati.weatherserver.weather.model.WeatherBuilder;
import com.chrisbarbati.weatherserver.websocket.WeatherWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Component to handle broadcasting weather data on the websocket at set intervals
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
@Component
public class WebsocketWeatherBroadcastScheduler {

    // Injected dependencies
    private final WeatherWebSocketHandler weatherWebSocketHandler;
    private final WeatherBuilder weatherBuilder;

    /**
     * Construct a new instance of {@link WebsocketWeatherBroadcastScheduler}.
     * <p>
     *     All parameters injected.
     * </p>
     *
     * @param weatherWebSocketHandler
     * @param weatherBuilder
     * @since 1.0.0
     * @author Christian Barbati
     */
    public WebsocketWeatherBroadcastScheduler(WeatherWebSocketHandler weatherWebSocketHandler, WeatherBuilder weatherBuilder) {
        this.weatherWebSocketHandler = weatherWebSocketHandler;
        this.weatherBuilder = weatherBuilder;
    }

    /**
     * At the specified interval, stream weather data on the websocket
     *
     * @since 1.0.0
     * @author Christian Barbati
     */
    @Scheduled(cron = "${scheduling.interval.cron.weather.broadcast}")
    public void streamWeatherData() throws Exception {
        Weather weather = weatherBuilder.build();
        weatherWebSocketHandler.broadcastWeather(weather);
    }
}

