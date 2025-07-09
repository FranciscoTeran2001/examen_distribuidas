package CareNotifier.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class CriticalAlertDTO {
    private String alertId;
    private String type;
    private String deviceId;
    private BigDecimal value;
    private BigDecimal threshold;
    private Instant timestamp;
}