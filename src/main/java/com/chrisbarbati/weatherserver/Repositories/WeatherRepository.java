package com.chrisbarbati.weatherserver.Repositories;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * Repository class to handle database operations for the WeatherEntity class.
 *
 */

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    /**
     * Hibernate ORM will handle the sorting of the data based on the naming convention.
     * @return A list of all WeatherEntity objects sorted by date in descending order.
     */
    public List<WeatherEntity> findAllByOrderByDstampDesc();

    /**
     * Get weather data from within a given date range
     * @param start Date object representing the start of the date range
     * @param end Date object representing the end of the date range
     * @return
     */
    public List<WeatherEntity> findByDstampBetweenOrderByDstampDesc(Date start, Date end);
}