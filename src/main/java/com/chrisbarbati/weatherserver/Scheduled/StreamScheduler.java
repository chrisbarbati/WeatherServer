package com.chrisbarbati.weatherserver.Scheduled;

import com.chrisbarbati.weatherserver.Builder.WeatherBuilder;
import com.chrisbarbati.weatherserver.Models.Weather;
import com.chrisbarbati.weatherserver.Streaming.WeatherWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler class for streaming weather data via WebSocket.
 */

@Component
public class StreamScheduler {

    private final WeatherWebSocketHandler myWebSocketHandler;
    private final WeatherBuilder weatherBuilder;

    public StreamScheduler(WeatherWebSocketHandler myWebSocketHandler, WeatherBuilder weatherBuilder) {
        this.myWebSocketHandler = myWebSocketHandler;
        this.weatherBuilder = weatherBuilder;
    }

    // Stream weather data every second
    @Scheduled(cron = "* * * * * *")
    public void streamWeatherData() throws Exception {
        Weather weather = weatherBuilder.getWeather();
        myWebSocketHandler.broadcastWeather(weather);
    }
}

