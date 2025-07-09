package CareNotifier.repository;

import CareNotifier.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findByStatus(String status);

    List<Notification> findByTimestampBetween(Instant start, Instant end);

    List<Notification> findByPriority(String priority);

    List<Notification> findByEventType(String eventType);
}