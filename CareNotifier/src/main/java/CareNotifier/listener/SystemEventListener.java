package CareNotifier.listener;


import CareNotifier.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SystemEventListener {

    private final NotificationService notificationService;

    public SystemEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "system.events.queue")
    public void handleSystemEvent(String message) {
        notificationService.processSystemEvent(message);
    }
}