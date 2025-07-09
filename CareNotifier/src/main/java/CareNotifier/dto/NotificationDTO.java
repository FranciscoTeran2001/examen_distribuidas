package CareNotifier.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class NotificationDTO {
    private String notificationId;
    private String eventType;
    private String recipient;
    private String message;
    private String priority; // EMERGENCY, WARNING, INFO
    private Instant timestamp;
    private List<String> channels; // EMAIL, SMS, PUSH
}