package CareNotifier.controller;

import CareNotifier.dto.NotificationDTO;
import CareNotifier.entity.Notification;
import CareNotifier.repository.NotificationRepository;
import CareNotifier.service.ExternalServiceSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/care-notifier")
public class NotificationController {

    private final ExternalServiceSimulator externalService;

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationController(ExternalServiceSimulator externalService) {
        this.externalService = externalService;
    }

    @PostMapping("/mock-email")
    public ResponseEntity<String> mockEmail(@RequestBody NotificationDTO notification) {
        externalService.simulateEmail(notification);
        return ResponseEntity.ok("Email simulation received");
    }

    @PostMapping("/mock-sms")
    public ResponseEntity<String> mockSMS(@RequestBody NotificationDTO notification) {
        externalService.simulateSMS(notification);
        return ResponseEntity.ok("SMS simulation received");
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("CareNotifier is running");
    }

    // Endpoint para ver todas las notificaciones guardadas
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    // Endpoint para ver notificaciones por tipo
    @GetMapping("/notifications/{eventType}")
    public ResponseEntity<List<Notification>> getNotificationsByType(@PathVariable String eventType) {
        List<Notification> notifications = notificationRepository.findByEventType(eventType);
        return ResponseEntity.ok(notifications);
    }
}