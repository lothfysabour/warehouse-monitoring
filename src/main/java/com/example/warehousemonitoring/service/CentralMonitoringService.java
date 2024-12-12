package com.example.warehousemonitoring.service;

import com.example.warehousemonitoring.configuration.SensorThreshold;
import com.example.warehousemonitoring.dto.SensorMeasurement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CentralMonitoringService {

    private final SensorThreshold sensorThreshold;

    public void evaluateMeasurement(SensorMeasurement measurement) {
        boolean alarm = false;
        String temperatureTreshold = sensorThreshold.getTemperature().getThreshold();
        String humidityTreshold = sensorThreshold.getHumidity().getThreshold();

        if("temperature".equalsIgnoreCase(measurement.getType())
                && measurement.getValue() > Double.parseDouble(temperatureTreshold)) {
            alarm = true;
        } else if("humidity".equalsIgnoreCase(measurement.getType())
                && measurement.getValue() > Double.parseDouble(humidityTreshold)) {
            alarm = true;
        }

        if(alarm) {
            logAlarm(measurement);
        }
    }

    private void logAlarm(SensorMeasurement measurement) {
        log.info(
                "ALARM! Sensor {} ({}) reported a critical value: {}",
                measurement.getSensorId(),
                measurement.getType(),
                measurement.getValue()
                         );
    }
}
