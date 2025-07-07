package PatientDataCollector.controller;


import PatientDataCollector.dto.VitalSignRequestDTO;
import PatientDataCollector.dto.VitalSignResponseDTO;
import PatientDataCollector.service.VitalSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("/vital-signs")
public class VitalSignController {

    @Autowired
    private VitalSignService vitalSignService;

    @PostMapping
    public ResponseEntity<VitalSignResponseDTO> receiveVitalSign(
            @RequestBody VitalSignRequestDTO requestDTO) {

        VitalSignResponseDTO response = vitalSignService.saveVitalSign(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<List<VitalSignResponseDTO>> getVitalSignsByDevice(
            @PathVariable String deviceId) {

        List<VitalSignResponseDTO> vitalSigns = vitalSignService.getVitalSignsByDevice(deviceId);
        return ResponseEntity.ok(vitalSigns);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("PatientDataCollector is running");
    }
}