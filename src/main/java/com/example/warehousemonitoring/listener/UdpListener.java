package com.example.warehousemonitoring.listener;

import com.example.warehousemonitoring.configuration.SensorThreshold;
import com.example.warehousemonitoring.processor.MeasurementProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

@Slf4j
@Component
public class UdpListener {

    private final SensorThreshold sensorThreshold;
    private final MeasurementProcessor processor;

    public UdpListener(final MeasurementProcessor processor, final SensorThreshold sensorThreshold) {
        this.processor = processor;
        this.sensorThreshold = sensorThreshold;
        startListening();
    }

    private void startListening() {
        String temperaturePort = sensorThreshold.getTemperature().getPort();
        String humidityPort = sensorThreshold.getHumidity().getPort();
        new Thread(() -> listen(Integer.parseInt(temperaturePort), "temperature")).start();
        new Thread(() -> listen(Integer.parseInt(humidityPort), "humidity")).start();
    }

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
