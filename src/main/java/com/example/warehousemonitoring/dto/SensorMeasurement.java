package com.example.warehousemonitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Data
public class SensorMeasurement implements Serializable {
    private String sensorId;
    private String type;
    private double value;
}
