package com.chrisbarbati.weatherserver.Repositories;

import com.chrisbarbati.weatherserver.Entities.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * @return A list of all WeatherEntity objects sorted by date in ascending order.
     */
    public List<WeatherEntity> findAllByOrderByDstampAsc();

}