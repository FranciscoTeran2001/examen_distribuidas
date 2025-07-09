package CareNotifier.listener;

import CareNotifier.dto.CriticalAlertDTO;
import CareNotifier.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AlertListener {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public AlertListener(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "critical.alerts.queue")
    public void handleCriticalAlert(String message) {
        try {
            System.out.println("=== CareNotifier recibió alerta crítica ===");
            System.out.println("Mensaje: " + message);

            CriticalAlertDTO alert = objectMapper.readValue(message, CriticalAlertDTO.class);
            System.out.println("Alerta deserializada: " + alert.getAlertId());

            notificationService.processCriticalAlert(alert);
        } catch (Exception e) {
            System.err.println("ERROR al procesar alerta en CareNotifier: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "warning.alerts.queue")
    public void handleWarningAlert(String message) {
        try {
            CriticalAlertDTO alert = objectMapper.readValue(message, CriticalAlertDTO.class);
            notificationService.processWarningAlert(alert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "info.alerts.queue")
    public void handleInfoAlert(String message) {
        try {
            CriticalAlertDTO alert = objectMapper.readValue(message, CriticalAlertDTO.class);
            notificationService.processInfoAlert(alert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}