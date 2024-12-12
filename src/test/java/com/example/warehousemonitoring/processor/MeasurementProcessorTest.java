package com.example.warehousemonitoring.processor;

import com.example.warehousemonitoring.dto.SensorMeasurement;
import com.example.warehousemonitoring.service.CentralMonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MeasurementProcessorTest {

    private CentralMonitoringService monitoringService;
    private MeasurementProcessor measurementProcessor;

    @BeforeEach
    void setUp() {
        monitoringService = mock(CentralMonitoringService.class);
        measurementProcessor = new MeasurementProcessor(monitoringService);
    }

    @Test
    void testProcessMeasurement() {
        // Given
        String data = "sensorId=123;value=45.67";
        String type = "temperature";

        // When
        measurementProcessor.processMeasurement(data, type);

        // Then
        ArgumentCaptor<SensorMeasurement> captor = ArgumentCaptor.forClass(SensorMeasurement.class);
        verify(monitoringService).evaluateMeasurement(captor.capture());

        SensorMeasurement capturedMeasurement = captor.getValue();
        assertEquals("123", capturedMeasurement.getSensorId());
        assertEquals("temperature", capturedMeasurement.getType());
        assertEquals(45.67, capturedMeasurement.getValue());
    }
}
