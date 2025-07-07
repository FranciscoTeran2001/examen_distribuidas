package CareNotifier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionDTO {
    private Long idNotificacion;
    private TipoEvento tipoEvento;
    private String destinatario;
    private EstadoNotificacion estado;
    private LocalDateTime fechaHora;
    private PrioridadNotificacion prioridad;
}
