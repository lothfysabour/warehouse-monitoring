package com.example.warehousemonitoring.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * *
 * Represents the thresholds for temperature and humidity sensors.
 * This class provides configuration for the ports and thresholds used
 * by each sensor type in the Central Monitoring Service.
 *
 * <p>The thresholds are used to determine when an alarm should be raised
 * for a given sensor measurement.</p>
 *
 * <h3>Specifications:</h3>
 * <ul>
 *     <li>Humidity:
 *         <ul>
 *             <li>UDP Port: 3355</li>
 *             <li>Threshold: 50%</li>
 *         </ul>
 *     </li>
 *     <li>Temperature:
 *         <ul>
 *             <li>UDP Port: 3344</li>
 *             <li>Threshold: 35°C</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author @lothfy
 */
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
        /**
         * The UDP port used by humidity sensors to send measurements.
         * Example: "3355".
         */
        private String port;
        /**
         * The threshold value for humidity measurements.
         * Example: "50" for 50%.
         */
        private String threshold;
    }

    @Getter
    @Setter
    @Configuration
    public static class Temperature {
        /**
         * The UDP port used by temperature sensors to send measurements.
         * Example: "3344".
         */
        private String port;
        /**
         * The threshold value for temperature measurements.
         * Example: "35" for 35°C.
         */
        private String threshold;
    }

}
