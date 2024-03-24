package com.chrisbarbati.weatherserver.Scheduled;

import com.chrisbarbati.weatherserver.Services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

import static org.mockito.Mockito.*;

public class WeatherDataSchedulerTest {

    @InjectMocks
    private WeatherDataScheduler weatherDataScheduler;

    @Mock
    private WeatherService weatherService;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private ScheduledFuture<?> scheduledFuture;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test that the saveWeatherData method is called when the scheduler is
     * triggered.
     */

    @Test
    @DisplayName("Should schedule saveWeatherData() task")
    public void shouldScheduleWeatherDataSavingTask() {
        doReturn(scheduledFuture).when(taskScheduler).schedule(any(Runnable.class), any(CronTrigger.class));

        weatherDataScheduler.saveWeatherData();

        verify(weatherService, times(1)).saveWeatherData();
    }
}