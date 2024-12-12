package com.example.warehousemonitoring.processor;

import com.example.warehousemonitoring.dto.SensorMeasurement;
import com.example.warehousemonitoring.service.CentralMonitoringService;
import org.springframework.stereotype.Component;

@Component
public class MeasurementProcessor {

    private final CentralMonitoringService monitoringService;

    public MeasurementProcessor(CentralMonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    public void processMeasurement(String data, String type) {
        String[] parts = data.split(";");
        String sensorId = parts[0].split("=")[1];
        double value = Double.parseDouble(parts[1].split("=")[1]);

        SensorMeasurement measurement = new SensorMeasurement(sensorId, type, value);
        monitoringService.evaluateMeasurement(measurement);
    }
}
