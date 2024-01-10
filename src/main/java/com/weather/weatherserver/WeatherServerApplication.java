package com.weather.weatherserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class WeatherServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherServerApplication.class, args);
    }
}
