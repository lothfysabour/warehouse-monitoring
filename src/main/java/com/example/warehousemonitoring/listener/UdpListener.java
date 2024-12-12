package com.example.warehousemonitoring.listener;

import com.example.warehousemonitoring.configuration.SensorThreshold;
import com.example.warehousemonitoring.processor.MeasurementProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Listens for UDP messages from temperature and humidity sensors and processes them.
 * The listener operates on separate threads for each sensor type, reading from the
 * configured UDP ports as specified in {@link SensorThreshold}.
 *
 * <p>The received measurements are parsed and handed off to the {@link MeasurementProcessor}
 * for further processing and validation against thresholds.</p>
 *
 * <h3>Features:</h3>
 * <ul>
 *     <li>Listens to temperature sensor data on the port configured in {@code SensorThreshold.Temperature}.</li>
 *     <li>Listens to humidity sensor data on the port configured in {@code SensorThreshold.Humidity}.</li>
 *     <li>Processes received data using the {@code MeasurementProcessor}.</li>
 * </ul>
 *
 * <h3>Error Handling:</h3>
 * <p>Logs errors that occur during socket operations.</p>
 *
 * @author @lothfy
 */
@Slf4j
@Component
public class UdpListener {

    private final SensorThreshold sensorThreshold;
    private final MeasurementProcessor processor;

    /**
     * Initializes the UDP listener with the given processor and sensor thresholds.
     *
     * @param processor the measurement processor
     * @param sensorThreshold the sensor thresholds and ports
     */
    public UdpListener(final MeasurementProcessor processor, final SensorThreshold sensorThreshold) {
        this.processor = processor;
        this.sensorThreshold = sensorThreshold;
        startListening();
    }

    /**
     * Starts listening for UDP packets on separate threads for temperature and humidity sensors.
     */
    private void startListening() {
        String temperaturePort = sensorThreshold.getTemperature().getPort();
        String humidityPort = sensorThreshold.getHumidity().getPort();
        new Thread(() -> listen(Integer.parseInt(temperaturePort), "temperature")).start();
        new Thread(() -> listen(Integer.parseInt(humidityPort), "humidity")).start();
    }

    /**
     * Listens for UDP packets on the specified port and processes them.
     *
     * @param port the UDP port to listen on
     * @param type the type of sensor (e.g., "temperature" or "humidity")
     */
    private void listen(int port, String type) {
        try(DatagramSocket socket = new DatagramSocket(port)) {
            byte[] buffer = new byte[1024];

            while(true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength());
                processor.processMeasurement(data, type);
            }
        } catch(Exception e) {
            log.error("Error when reading socket", e);
        }
    }
}
