package com.chrisbarbati.weatherserver.weather.repository;

import com.chrisbarbati.weatherserver.weather.entity.WeatherEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

/**
 * MariaDB uses {@code UNIX_TIMESTAMP}; H2 uses {@code DATEDIFF} so the same
 * grouping can be tested off the Pi.
 *
 * @since 1.0.0
 * @author Christian Barbati
 */
public class WeatherRepositoryImpl implements WeatherRepositoryCustom {

    private static final String MARIADB = "SELECT w.* FROM weather w "
            + "INNER JOIN ("
            + "  SELECT MAX(id) AS id FROM weather "
            + "  WHERE dstamp >= :start AND dstamp <= :end "
            + "  GROUP BY FLOOR(UNIX_TIMESTAMP(dstamp) / :bucketSeconds)"
            + ") sampled ON w.id = sampled.id "
            + "ORDER BY w.dstamp DESC";

    private static final String H2 = "SELECT w.* FROM weather w "
            + "INNER JOIN ("
            + "  SELECT MAX(id) AS id FROM weather "
            + "  WHERE dstamp >= :start AND dstamp <= :end "
            + "  GROUP BY FLOOR(DATEDIFF('SECOND', TIMESTAMP '1970-01-01 00:00:00', dstamp) / :bucketSeconds)"
            + ") sampled ON w.id = sampled.id "
            + "ORDER BY w.dstamp DESC";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<WeatherEntity> findSampled(Date start, Date end, long bucketSeconds) {
        return entityManager.createNativeQuery(sql(), WeatherEntity.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("bucketSeconds", bucketSeconds)
                .getResultList();
    }

    private String sql() {
        try {
            String dialect = entityManager.getEntityManagerFactory()
                    .unwrap(org.hibernate.engine.spi.SessionFactoryImplementor.class)
                    .getJdbcServices()
                    .getDialect()
                    .getClass()
                    .getName();
            if (dialect.contains("H2")) {
                return H2;
            }
        } catch (RuntimeException ignored) {
            // Production on the Pi is MariaDB.
        }
        return MARIADB;
    }
}
