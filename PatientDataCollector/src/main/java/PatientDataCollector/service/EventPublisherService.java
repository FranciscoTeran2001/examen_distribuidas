package PatientDataCollector.service;

import PatientDataCollector.dto.NewVitalSignEventDTO;
import PatientDataCollector.entity.VitalSign;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EventPublisherService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishNewVitalSignEvent(VitalSign vitalSign) {
        try {
            System.out.println("=== PatientDataCollector publicando evento ===");
            System.out.println("Dispositivo: " + vitalSign.getDeviceId() + ", Tipo: " + vitalSign.getType() + ", Valor: " + vitalSign.getValue());

            NewVitalSignEventDTO eventDTO = NewVitalSignEventDTO.builder()
                    .eventId("EVT-" + UUID.randomUUID())
                    .deviceId(vitalSign.getDeviceId())
                    .type(vitalSign.getType())
                    .value(vitalSign.getValue())
                    .timestamp(vitalSign.getTimestamp())
                    .eventTimestamp(java.time.LocalDateTime.now())
                    .source("PatientDataCollector")
                    .version("1.0.0")
                    .correlationId(UUID.randomUUID().toString())
                    .build();

            // Convertir a JSON y enviar como String
            String jsonMessage = objectMapper.writeValueAsString(eventDTO);
            System.out.println("Enviando mensaje a RabbitMQ: " + jsonMessage);

            rabbitTemplate.convertAndSend("new.vital.sign.queue", jsonMessage);
            System.out.println("Mensaje enviado exitosamente a la cola new.vital.sign.queue");

        } catch (Exception e) {
            System.err.println("ERROR al publicar evento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}