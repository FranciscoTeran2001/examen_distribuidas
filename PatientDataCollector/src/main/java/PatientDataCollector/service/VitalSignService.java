package PatientDataCollector.service;

import PatientDataCollector.dto.VitalSignRequestDTO;
import PatientDataCollector.dto.VitalSignResponseDTO;
import PatientDataCollector.entity.VitalSign;
import PatientDataCollector.repository.VitalSignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VitalSignService {

    @Autowired
    private VitalSignRepository vitalSignRepository;

    @Autowired
    private EventPublisherService eventPublisherService;

    public VitalSignResponseDTO saveVitalSign(VitalSignRequestDTO requestDTO) {

        // Validar rangos de valores
        validateVitalSignValues(requestDTO);

        // Crear entidad
        VitalSign vitalSign = new VitalSign();
        vitalSign.setDeviceId(requestDTO.getDeviceId());
        vitalSign.setType(requestDTO.getType());
        vitalSign.setValue(requestDTO.getValue());
        vitalSign.setTimestamp(requestDTO.getTimestamp() != null ?
                requestDTO.getTimestamp() : LocalDateTime.now());

        // Guardar en base de datos
        VitalSign savedVitalSign = vitalSignRepository.save(vitalSign);

        // Publicar evento
        eventPublisherService.publishNewVitalSignEvent(savedVitalSign);

        // Retornar respuesta
        return VitalSignResponseDTO.builder()
                .id(savedVitalSign.getId())
                .deviceId(savedVitalSign.getDeviceId())
                .type(savedVitalSign.getType())
                .value(savedVitalSign.getValue())
                .timestamp(savedVitalSign.getTimestamp())
                .status("SUCCESS")
                .build();
    }

    public List<VitalSignResponseDTO> getVitalSignsByDevice(String deviceId) {

        List<VitalSign> vitalSigns = vitalSignRepository.findByDeviceIdOrderByTimestampDesc(deviceId);

        return vitalSigns.stream()
                .map(vs -> VitalSignResponseDTO.builder()
                        .id(vs.getId())
                        .deviceId(vs.getDeviceId())
                        .type(vs.getType())
                        .value(vs.getValue())
                        .timestamp(vs.getTimestamp())
                        .status("SUCCESS")
                        .build())
                .collect(Collectors.toList());
    }

    private void validateVitalSignValues(VitalSignRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BigDecimal value = requestDTO.getValue();

        if (value == null) {
            throw new RuntimeException("El valor no puede ser nulo");
        }

        switch (type) {
            case "heart-rate":
                if (value.compareTo(BigDecimal.valueOf(30)) < 0 || value.compareTo(BigDecimal.valueOf(200)) > 0) {
                    throw new RuntimeException("Frecuencia cardíaca fuera de rango: " + value);
                }
                break;
            case "blood-pressure":
                if (value.compareTo(BigDecimal.valueOf(50)) < 0 || value.compareTo(BigDecimal.valueOf(250)) > 0) {
                    throw new RuntimeException("Presión arterial fuera de rango: " + value);
                }
                break;
            case "oxygen-saturation":
                if (value.compareTo(BigDecimal.valueOf(70)) < 0 || value.compareTo(BigDecimal.valueOf(100)) > 0) {
                    throw new RuntimeException("Saturación de oxígeno fuera de rango: " + value);
                }
                break;
            case "temperature":
                if (value.compareTo(BigDecimal.valueOf(35)) < 0 || value.compareTo(BigDecimal.valueOf(42)) > 0) {
                    throw new RuntimeException("Temperatura fuera de rango: " + value);
                }
                break;
        }
    }
}