package com.chrisbarbati.weatherserver.Scheduled;

import com.chrisbarbati.weatherserver.Models.weather.DefaultWeatherBuilder;
import com.chrisbarbati.weatherserver.Models.weather.Weather;
import com.chrisbarbati.weatherserver.Streaming.WeatherWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler class for streaming weather data via WebSocket.
 */

@Component
public class StreamScheduler {

    private final WeatherWebSocketHandler myWebSocketHandler;
    private final DefaultWeatherBuilder defaultWeatherBuilder;

    public StreamScheduler(WeatherWebSocketHandler myWebSocketHandler, DefaultWeatherBuilder weatherBuilder) {
        this.myWebSocketHandler = myWebSocketHandler;
        this.defaultWeatherBuilder = weatherBuilder;
    }

    // Stream weather data every second
//    @Scheduled(cron = "* * * * * *")
//    public void streamWeatherData() throws Exception {
//        Weather weather = defaultWeatherBuilder.getWeather();
//        myWebSocketHandler.broadcastWeather(weather);
//    }
}

