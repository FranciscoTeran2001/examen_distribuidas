package CareNotifier.controller;

import CareNotifier.dto.NotificacionDTO;
import CareNotifier.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
@Slf4j
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionDTO> procesarNotificacion(@RequestBody NotificacionDTO notificacion) {
        log.info("Recibida nueva notificación: {}", notificacion);
        NotificacionDTO result = notificacionService.procesarNotificacion(notificacion);
        return ResponseEntity.ok(result);
    }
}