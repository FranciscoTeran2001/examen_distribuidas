package PatientDataCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewVitalSignEventDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String eventId;
    private String eventType;
    private String deviceId;
    private String type;
    private BigDecimal value;
    private LocalDateTime timestamp;
    private LocalDateTime eventTimestamp;
    private String source;
    private String version;
    private String correlationId;
}