package com.example.warehousemonitoring;

import com.example.warehousemonitoring.dto.SensorMeasurement;
import com.example.warehousemonitoring.service.CentralMonitoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WarehouseMonitoringApplicationTests {

    @Autowired
    private CentralMonitoringService monitoringService;

    @Test
    void testTemperatureAlarm_thresholdExceeded() {
        // Given
        SensorMeasurement measurement = SensorMeasurement.builder()
                .sensorId("t1")
                .type("temperature")
                .value(36.0)
                .build();
        // When
        monitoringService.evaluateMeasurement(measurement);

    }

    @Test
    void testHumidityAlarm_thresholdExceeded() {
        // Given
        SensorMeasurement measurement = SensorMeasurement.builder()
                .sensorId("h1")
                .type("humidity")
                .value(51.0)
                .build();

        //Then
        monitoringService.evaluateMeasurement(measurement);
    }

    @Test
    void testNoAlarm() {
        // Given
        SensorMeasurement measurement = SensorMeasurement.builder()
                .sensorId("t1")
                .type("temperature")
                .value(25.0)
                .build();
        // Then    
        monitoringService.evaluateMeasurement(measurement);
    }

}
