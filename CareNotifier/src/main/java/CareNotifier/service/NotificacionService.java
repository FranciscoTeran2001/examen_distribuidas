package CareNotifier.service;

import CareNotifier.dto.NotificacionDTO;
import CareNotifier.entity.Notificacion;
import CareNotifier.enums.EstadoNotificacion;
import CareNotifier.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionDTO procesarNotificacion(NotificacionDTO notificacionDTO) {
        log.info("Procesando notificación: {}", notificacionDTO);

        Notificacion notificacion = toEntity(notificacionDTO);
        notificacion.setFechaHora(LocalDateTime.now());
        notificacion.setEstado(EstadoNotificacion.PENDIENTE);

        notificacion = notificacionRepository.save(notificacion);
        simularEnvioNotificacion(notificacion);

        return toDTO(notificacion);
    }

    private void simularEnvioNotificacion(Notificacion notificacion) {
        try {
            switch (notificacion.getPrioridad()) {
                case EMERGENCIA -> {
                    log.info("Enviando notificación de emergencia a: {}", notificacion.getDestinatario());
                    enviarNotificacionInmediata(notificacion);
                }
                case ADVERTENCIA, INFORMACION -> {
                    log.info("Agregando notificación a cola de baja prioridad para: {}", notificacion.getDestinatario());
                    agregarAColaAgrupada(notificacion);
                }
            }
            notificacion.setEstado(EstadoNotificacion.ENVIADA);
        } catch (Exception e) {
            log.error("Error al enviar notificación", e);
            notificacion.setEstado(EstadoNotificacion.FALLIDA);
        }
        notificacionRepository.save(notificacion);
    }

    private void enviarNotificacionInmediata(Notificacion notificacion) {
        log.info("Simulando envío inmediato de notificación tipo: {}", notificacion.getTipoEvento());
        // Aquí iría la lógica de envío real
    }

    private void agregarAColaAgrupada(Notificacion notificacion) {
        log.info("Agregando a cola agrupada notificación tipo: {}", notificacion.getTipoEvento());
        // Aquí iría la lógica de agrupación
    }

    private Notificacion toEntity(NotificacionDTO dto) {
        return Notificacion.builder()
                .idNotificacion(dto.getIdNotificacion())
                .tipoEvento(dto.getTipoEvento())
                .destinatario(dto.getDestinatario())
                .estado(dto.getEstado())
                .fechaHora(dto.getFechaHora())
                .prioridad(dto.getPrioridad())
                .build();
    }

    private NotificacionDTO toDTO(Notificacion entity) {
        return NotificacionDTO.builder()
                .idNotificacion(entity.getIdNotificacion())
                .tipoEvento(entity.getTipoEvento())
                .destinatario(entity.getDestinatario())
                .estado(entity.getEstado())
                .fechaHora(entity.getFechaHora())
                .prioridad(entity.getPrioridad())
                .build();
    }
}