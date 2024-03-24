package com.chrisbarbati.weatherserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeatherServerApplicationTests {

    /**
     * This test is currently disabled as it will always fail unless developing
     * on the RaspberryPi. Context will not load as the Windows machine I develop
     * on does not have an I2C bus.
     */

    /*
    @Test
    void contextLoads() {
    }
    */
}
