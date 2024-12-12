package com.example.warehousemonitoring.processor;

import com.example.warehousemonitoring.dto.SensorMeasurement;
import com.example.warehousemonitoring.service.CentralMonitoringService;
import org.springframework.stereotype.Component;

/**
 * Processes sensor measurements received via UDP and forwards them
 * to the {@link CentralMonitoringService} for evaluation.
 *
 * <p>This class parses the incoming measurement data, extracts the sensor ID
 * and value, and creates a {@link SensorMeasurement} object. The measurement
 * is then passed to the monitoring service to determine if it violates
 * predefined thresholds.</p>
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Parses incoming measurement data in the format {@code sensor_id=<id>;value=<value>}.</li>
 *     <li>Identifies the sensor type (e.g., "temperature", "humidity").</li>
 *     <li>Delegates threshold evaluation to the Central Monitoring Service.</li>
 * </ul>
 *
 * <h3>Error Handling:</h3>
 * <p>Assumes the incoming data format is valid. Any parsing errors or invalid
 * formats will result in runtime exceptions.</p>
 *
 * @author @lothfy
 */
@Component
public class MeasurementProcessor {

    /**
     * The service responsible for evaluating sensor measurements against thresholds.
     */
    private final CentralMonitoringService monitoringService;

    /**
     * Constructs a MeasurementProcessor with the specified monitoring service.
     *
     * @param monitoringService the central monitoring service
     */
    public MeasurementProcessor(CentralMonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    /**
     * Processes a single measurement received from a sensor.
     *
     * <p>Parses the data string to extract the sensor ID and measurement value.
     * Constructs a {@link SensorMeasurement} and forwards it to the monitoring service.</p>
     *
     * @param data the raw measurement data in the format {@code sensor_id=<id>;value=<value>}
     * @param type the type of sensor (e.g., "temperature" or "humidity")
     */
    public void processMeasurement(String data, String type) {
        String[] parts = data.split(";");
        String sensorId = parts[0].split("=")[1];
        double value = Double.parseDouble(parts[1].split("=")[1]);

        SensorMeasurement measurement = new SensorMeasurement(sensorId, type, value);
        monitoringService.evaluateMeasurement(measurement);
    }
}
