package CareNotifier.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PriorityService {

    public List<String> getChannelsForPriority(String priority) {
        return switch (priority) {
            case "EMERGENCY" -> Arrays.asList("EMAIL", "SMS", "PUSH");
            case "WARNING" -> Arrays.asList("EMAIL", "PUSH");
            case "INFO" -> List.of("EMAIL");
            default -> List.of("EMAIL");
        };
    }
}