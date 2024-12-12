package com.example.warehousemonitoring.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties( prefix = "sensor-threshold")
public class SensorThreshold {

    private Humidity humidity;
    private Temperature temperature;

    @Getter
    @Setter
    @Configuration
    public static class Humidity {
        private String port;
        private String threshold;
    }

    @Getter
    @Setter
    @Configuration
    public static class Temperature {
        private String port;
        private String threshold;
    }

}
