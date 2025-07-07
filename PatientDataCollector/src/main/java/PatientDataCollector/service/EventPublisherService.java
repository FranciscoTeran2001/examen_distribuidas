package PatientDataCollector.service;

import PatientDataCollector.dto.NewVitalSignEventDTO;
import PatientDataCollector.entity.VitalSign;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EventPublisherService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishNewVitalSignEvent(VitalSign vitalSign) {

        // Crear evento DTO
        NewVitalSignEventDTO eventDTO = new NewVitalSignEventDTO();
        eventDTO.setEventId("EVT-" + UUID.randomUUID().toString());
        eventDTO.setDeviceId(vitalSign.getDeviceId());
        eventDTO.setType(vitalSign.getType());
        eventDTO.setValue(vitalSign.getValue());
        eventDTO.setTimestamp(vitalSign.getTimestamp());

        // Publicar evento
        rabbitTemplate.convertAndSend("new.vital.sign.queue", eventDTO);
    }
}