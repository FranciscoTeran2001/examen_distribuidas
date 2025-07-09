package CareNotifier.service;

import CareNotifier.dto.CriticalAlertDTO;
import CareNotifier.dto.NotificationDTO;
import CareNotifier.entity.Notification;
import CareNotifier.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ExternalServiceSimulator externalService;
    private final PriorityService priorityService;

    // Almacenamiento temporal para alertas de baja prioridad
    private final Map<String, List<NotificationDTO>> groupedNotifications = new ConcurrentHashMap<>();

    public NotificationService(NotificationRepository notificationRepository,
                               ExternalServiceSimulator externalService,
                               PriorityService priorityService) {
        this.notificationRepository = notificationRepository;
        this.externalService = externalService;
        this.priorityService = priorityService;
    }

    public void processCriticalAlert(CriticalAlertDTO alert) {
        System.out.println("=== NotificationService procesando alerta crítica ===");
        System.out.println("Alert ID: " + alert.getAlertId() + ", Tipo: " + alert.getType());

        NotificationDTO notification = createNotification(alert, "EMERGENCY");
        System.out.println("Notificación creada: " + notification.getNotificationId());

        sendImmediateNotification(notification);
        System.out.println("Proceso de notificación completado");
    }

    public void processWarningAlert(CriticalAlertDTO alert) {
        NotificationDTO notification = createNotification(alert, "WARNING");
        sendImmediateNotification(notification);
    }

    public void processInfoAlert(CriticalAlertDTO alert) {
        NotificationDTO notification = createNotification(alert, "INFO");
        groupNotification(notification);
    }

    public void processSystemEvent(String event) {
        NotificationDTO notification = new NotificationDTO();
        notification.setNotificationId("SYS-" + Instant.now().toEpochMilli());
        notification.setEventType("SYSTEM_EVENT");
        notification.setMessage(event);
        notification.setPriority("WARNING");
        notification.setTimestamp(Instant.now());

        sendImmediateNotification(notification);
    }

    private NotificationDTO createNotification(CriticalAlertDTO alert, String priority) {
        NotificationDTO notification = new NotificationDTO();
        notification.setNotificationId("NOT-" + Instant.now().toEpochMilli());
        notification.setEventType(alert.getType());
        notification.setMessage(buildAlertMessage(alert));
        notification.setPriority(priority);
        notification.setTimestamp(Instant.now());
        notification.setChannels(priorityService.getChannelsForPriority(priority));

        return notification;
    }

    private String buildAlertMessage(CriticalAlertDTO alert) {
        return String.format("ALERTA: %s - Dispositivo: %s - Valor: %s (Umbral: %s)",
                alert.getType(), alert.getDeviceId(), alert.getValue(), alert.getThreshold());
    }

    private void sendImmediateNotification(NotificationDTO notification) {
        try {
            // Simular envío por diferentes canales
            if (notification.getChannels().contains("EMAIL")) {
                externalService.simulateEmail(notification);
            }
            if (notification.getChannels().contains("SMS")) {
                externalService.simulateSMS(notification);
            }
            if (notification.getChannels().contains("PUSH")) {
                externalService.simulatePush(notification);
            }

            // Guardar en base de datos
            saveNotification(notification, "SENT");

        } catch (Exception e) {
            saveNotification(notification, "FAILED");
        }
    }

    private void groupNotification(NotificationDTO notification) {
        groupedNotifications
                .computeIfAbsent(notification.getPriority(), k -> new ArrayList<>())
                .add(notification);
    }

    @Scheduled(fixedRate = 1800000) // Cada 30 minutos
    public void sendGroupedNotifications() {
        groupedNotifications.forEach((priority, notifications) -> {
            if (!notifications.isEmpty()) {
                // Enviar todas las notificaciones agrupadas
                notifications.forEach(this::sendImmediateNotification);
                notifications.clear();
            }
        });
    }

    private void saveNotification(NotificationDTO dto, String status) {
        Notification notification = new Notification();
        notification.setNotificationId(dto.getNotificationId());
        notification.setEventType(dto.getEventType());
        notification.setRecipient("medical-team"); // Podría ser dinámico
        notification.setStatus(status);
        notification.setTimestamp(dto.getTimestamp());
        notification.setPriority(dto.getPriority());

        notificationRepository.save(notification);
    }

    @Scheduled(fixedRate = 60000) // Cada minuto
    public void retryFailedNotifications() {
        List<Notification> failedNotifications = notificationRepository.findByStatus("FAILED");

        failedNotifications.forEach(notification -> {
            if (notification.getRetryCount() < 3) {
                // Reintentar el envío
                NotificationDTO dto = new NotificationDTO();
                dto.setNotificationId(notification.getNotificationId());
                dto.setEventType(notification.getEventType());
                dto.setMessage("Reintento: " + notification.getEventType());
                dto.setPriority(notification.getPriority());
                dto.setTimestamp(Instant.now());
                dto.setChannels(priorityService.getChannelsForPriority(notification.getPriority()));

                sendImmediateNotification(dto);

                notification.setRetryCount(notification.getRetryCount() + 1);
                notificationRepository.save(notification);
            }
        });
    }
}