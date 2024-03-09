package com.chrisbarbati.weatherserver.Repositories;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class to handle database operations for the WeatherEntity class.
 *
 */

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    //Custom queries will be added here later
}