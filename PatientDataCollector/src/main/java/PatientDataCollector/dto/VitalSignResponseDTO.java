package PatientDataCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalSignResponseDTO {

    private Long id;
    private String deviceId;
    private String type;
    private BigDecimal value;
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private String status;
}