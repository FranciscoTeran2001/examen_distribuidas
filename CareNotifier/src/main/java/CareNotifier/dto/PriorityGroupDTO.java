package CareNotifier.dto;
import lombok.Data;
import java.util.List;

@Data
public class PriorityGroupDTO {
    private String priority;
    private List<NotificationDTO> notifications;
}