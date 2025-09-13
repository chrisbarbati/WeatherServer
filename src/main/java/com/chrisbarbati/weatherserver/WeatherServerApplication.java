package com.chrisbarbati.weatherserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.chrisbarbati.weatherserver.weather.repository")
@EnableScheduling
@EnableCaching
public class WeatherServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherServerApplication.class, args);
    }
}
