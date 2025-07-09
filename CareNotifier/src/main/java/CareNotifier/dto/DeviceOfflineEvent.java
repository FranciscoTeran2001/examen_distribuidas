package CareNotifier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class DeviceOfflineEvent {
    private String eventId;
    private String deviceId;
    private Instant lastActiveTime;
    private Instant timestamp;
}
