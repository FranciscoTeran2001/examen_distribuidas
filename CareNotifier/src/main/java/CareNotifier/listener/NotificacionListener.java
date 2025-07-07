package CareNotifier.listener;


import CareNotifier.dto.NotificacionDTO;
import CareNotifier.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificacionListener {

    private final NotificacionService notificacionService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void recibirNotificacion(@Payload NotificacionDTO notificacion) {
        try {
            log.info("Mensaje recibido: {}", notificacion);
            notificacionService.procesarNotificacion(notificacion);
        } catch (Exception e) {
            log.error("Error al procesar la notificación: {}", e.getMessage(), e);
            throw e;
        }
    }
}
