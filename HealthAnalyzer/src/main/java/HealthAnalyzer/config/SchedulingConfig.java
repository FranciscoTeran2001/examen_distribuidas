package HealthAnalyzer.config;

import HealthAnalyzer.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private AlertService alertService;

    // Reporte diario a medianoche (cada 24 horas)
    @Scheduled(cron = "0 0 0 * * ?")
    public void generateDailyReport() {
        try {
            System.out.println("=== EJECUTANDO REPORTE DIARIO ===");
            alertService.generateDailyReport();
            System.out.println("Reporte diario generado exitosamente");
        } catch (Exception e) {
            System.err.println("Error generando reporte diario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Verificación de dispositivos inactivos cada 6 horas
    @Scheduled(cron = "0 0 */6 * * ?")
    public void checkInactiveDevices() {
        try {
            System.out.println("=== VERIFICANDO DISPOSITIVOS INACTIVOS ===");
            alertService.checkInactiveDevices();
            System.out.println("Verificación de dispositivos completada");
        } catch (Exception e) {
            System.err.println("Error verificando dispositivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Limpieza de datos históricos antiguos (mensualmente)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void archiveOldData() {
        try {
            System.out.println("=== ARCHIVANDO DATOS ANTIGUOS ===");
            alertService.archiveOldData();
            System.out.println("Archivado de datos completado");
        } catch (Exception e) {
            System.err.println("Error archivando datos antiguos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Para testing - ejecutar cada minuto (puedes comentar en producción)
    @Scheduled(fixedRate = 60000)
    public void testScheduledTasks() {
        System.out.println("=== TEST: Tareas programadas activas - " + java.time.LocalDateTime.now() + " ===");
    }
}
