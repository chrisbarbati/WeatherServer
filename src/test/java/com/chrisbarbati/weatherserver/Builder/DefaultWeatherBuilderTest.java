package com.chrisbarbati.weatherserver.Builder;

import com.chrisbarbati.SenseHAT.SenseHAT;
import com.chrisbarbati.weatherserver.weather.model.DefaultWeatherBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultWeatherBuilderTest {

    @InjectMocks
    private DefaultWeatherBuilder defaultWeatherBuilder;

    @Mock
    private SenseHAT senseHAT;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Methods that create a new Weather object will inherently fail as I am developing on a Windows machine.
     *
     * Since the SenseHAT is a Raspberry Pi add-on, I cannot run that code on my machine. IntelliJ IDEA remote
     * development will not install properly on the Raspberry Pi.
     *
     * TODO: Get remote-development up and running so I can compile and test this on the Raspberry Pi
     */

    /**
     * Test the getWeather method with an invalid temperature unit
     */
//    @Test
//    @DisplayName("Should throw IllegalArgumentException for invalid temperature unit")
//    public void shouldThrowIllegalArgumentExceptionForInvalidTemperatureUnit() {
//        try {
//            defaultWeatherBuilder.getWeather("invalid", "millibar");
//        } catch (IllegalArgumentException e) {
//            assertEquals("Invalid temperature unit", e.getMessage());
//        }
//    }
//
//    /**
//     * Test the getWeather method with an invalid pressure unit
//     */
//    @Test
//    @DisplayName("Should throw IllegalArgumentException for invalid pressure unit")
//    public void shouldThrowIllegalArgumentExceptionForInvalidPressureUnit() {
//        try {
//            defaultWeatherBuilder.getWeather("celsius", "invalid");
//        } catch (IllegalArgumentException e) {
//            assertEquals("Invalid pressure unit", e.getMessage());
//        }
//    }
}