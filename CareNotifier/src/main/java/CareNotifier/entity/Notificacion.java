package CareNotifier.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    private String destinatario;

    @Enumerated(EnumType.STRING)
    private EstadoNotificacion estado;

    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private PrioridadNotificacion prioridad;
}
