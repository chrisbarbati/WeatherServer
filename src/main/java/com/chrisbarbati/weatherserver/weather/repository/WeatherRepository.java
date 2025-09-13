package com.chrisbarbati.weatherserver.weather.repository;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * Repository class to handle database operations for the WeatherEntity class.
 * <p>
 * Implementation provided by JPA.
 * </p>
 * @since 1.0.0
 * @author Christian Barbati
 */
@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Integer> {

    /**
     * Hibernate ORM will handle the sorting of the data based on the naming convention.
     *
     * @return A {@link List} of all {@link WeatherEntity} objects sorted by date in descending order.
     * @since 1.0.0
     * @author Christian Barbati
     */
    public List<WeatherEntity> findAllByOrderByDstampDesc();

    /**
     * Get weather data from within a given date range
     *
     * @param start {@link Date} object representing the start of the date range
     * @param end {@link Date} object representing the end of the date range
     * @return A {@link List} of all {@link WeatherEntity} objects within the specified range
     * @since 1.0.0
     * @author Christian Barbati
     */
    public List<WeatherEntity> findByDstampBetweenOrderByDstampDesc(Date start, Date end);
}