package HealthAnalyzer.Controller;

import HealthAnalyzer.entity.MedicalAlert;
import HealthAnalyzer.repository.MedicalAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/health-analyzer")
public class HealthAnalyzerController {

    @Autowired
    private MedicalAlertRepository alertRepository;

    // Endpoint opcional de salud (para monitoreo)
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("HealthAnalyzer is running");
    }

    // Endpoint para ver todas las alertas generadas
    @GetMapping("/alerts")
    public ResponseEntity<List<MedicalAlert>> getAllAlerts() {
        List<MedicalAlert> alerts = alertRepository.findAll();
        return ResponseEntity.ok(alerts);
    }

    // Endpoint para ver alertas por dispositivo
    @GetMapping("/alerts/{deviceId}")
    public ResponseEntity<List<MedicalAlert>> getAlertsByDevice(@PathVariable String deviceId) {
        List<MedicalAlert> alerts = alertRepository.findByDeviceId(deviceId);
        return ResponseEntity.ok(alerts);
    }

}