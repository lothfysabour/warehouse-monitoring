package com.example.warehousemonitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Data
public class SensorMeasurement implements Serializable {

    /**
     * Unique identifier of the sensor.
     * Example: "t1" for temperature sensors, "h1" for humidity sensors.
     */
    private String sensorId;

    /**
     * Type of sensor. Possible values:
     * <ul>
     *     <li>"temperature" for temperature sensors.</li>
     *     <li>"humidity" for humidity sensors.</li>
     * </ul>
     */
    private String type;

    /**
     * The measured value from the sensor.
     * Example: 30 for temperature sensors, 40 for humidity sensors.
     */
    private double value;
}
