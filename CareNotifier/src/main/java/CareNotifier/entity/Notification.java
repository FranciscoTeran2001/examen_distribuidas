package CareNotifier.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @Column(name = "notification_id")
    private String notificationId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "status", nullable = false)
    private String status; // SENT, FAILED, PENDING

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "priority", nullable = false)
    private String priority; // EMERGENCY, WARNING, INFO

    @Column(name = "retry_count")
    private Integer retryCount = 0;
}