package HealthAnalyzer.Listener;

import HealthAnalyzer.dto.NewVitalSignEventDTO;
import HealthAnalyzer.service.AlertService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VitalSignListener {

    @Autowired
    private AlertService alertService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "new.vital.sign.queue")
    public void handleVitalSignEvent(String message) {
        try {
            System.out.println("=== HealthAnalyzer recibió mensaje de RabbitMQ ===");
            System.out.println("Mensaje: " + message);

            NewVitalSignEventDTO event = objectMapper.readValue(message, NewVitalSignEventDTO.class);
            System.out.println("Evento deserializado correctamente: " + event.getEventId());

            alertService.processVitalSignEvent(event);
        } catch (Exception e) {
            System.err.println("ERROR al procesar evento en HealthAnalyzer: " + e.getMessage());
            e.printStackTrace();
            // Manejo de errores (ej: enviar a DLQ)
        }
    }
}