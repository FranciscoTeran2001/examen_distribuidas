package CareNotifier.service;


import CareNotifier.dto.NotificationDTO;
import org.springframework.stereotype.Service;

@Service
public class ExternalServiceSimulator {

    public void simulateEmail(NotificationDTO notification) {
        System.out.printf("[EMAIL SIMULATION] To: medical-team@hospital.com | Subject: %s | Body: %s%n",
                notification.getEventType(), notification.getMessage());
    }

    public void simulateSMS(NotificationDTO notification) {
        System.out.printf("[SMS SIMULATION] To: +593991234567 | Message: %s%n",
                notification.getMessage());
    }

    public void simulatePush(NotificationDTO notification) {
        System.out.printf("[PUSH SIMULATION] Device: ALL | Title: %s | Body: %s%n",
                notification.getEventType(), notification.getMessage());
    }
}