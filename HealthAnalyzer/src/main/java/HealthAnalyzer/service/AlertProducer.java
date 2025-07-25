package HealthAnalyzer.service;

import HealthAnalyzer.dto.DailyReportEventDTO;
import HealthAnalyzer.dto.DeviceOfflineEvent;
import HealthAnalyzer.dto.MedicalAlertDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendAlert(MedicalAlertDTO alert) {
        try {
            String json = objectMapper.writeValueAsString(alert);
            amqpTemplate.convertAndSend("critical.alerts.queue", json);
        } catch (Exception e) {
            e.printStackTrace();
            // Aquí podrías agregar el fallback a SQLite
        }
    }

    public void sendDeviceOfflineEvent(DeviceOfflineEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            amqpTemplate.convertAndSend("device-offline.queue", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // En AlertProducer.java
    public void sendDailyReport(DailyReportEventDTO report) {
        try {
            String json = objectMapper.writeValueAsString(report);
            amqpTemplate.convertAndSend("daily.report.queue", json);
        } catch (Exception e) {
            e.printStackTrace();
            // Aquí podrías agregar el fallback a SQLite
        }
    }
}