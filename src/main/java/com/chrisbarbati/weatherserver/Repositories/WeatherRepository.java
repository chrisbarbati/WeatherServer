package com.chrisbarbati.weatherserver.Repositories;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {
    //Repository interface that handles database functionality.

    //Custom queries will be added here later
}