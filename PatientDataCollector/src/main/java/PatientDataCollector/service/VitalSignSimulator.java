package PatientDataCollector.service;

import PatientDataCollector.dto.VitalSignRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.List;
import java.util.Arrays;

@Service
public class VitalSignSimulator {

    @Autowired
    private VitalSignService vitalSignService;

    private final Random random = new Random();

    // Lista de dispositivos simulados
    private final List<String> deviceIds = Arrays.asList(
        "DEV-001", "DEV-002", "DEV-003", "DEV-004", "DEV-005",
        "DEV-ICU-001", "DEV-ICU-002", "DEV-ER-001", "DEV-ER-002"
    );

    // Lista de tipos de signos vitales
    private final List<String> vitalSignTypes = Arrays.asList(
        "HEART_RATE", "OXYGEN", "BLOOD_PRESSURE"
    );

    // Simular signos vitales cada 30 segundos
    @Scheduled(fixedRate = 10000)
    public void simulateNormalVitalSigns() {
        try {
            String deviceId = getRandomDevice();
            String type = getRandomVitalSignType();
            BigDecimal value = generateNormalValue(type);

            VitalSignRequestDTO request = new VitalSignRequestDTO();
            request.setDeviceId(deviceId);
            request.setType(type);
            request.setValue(value);
            request.setTimestamp(LocalDateTime.now());

            System.out.println("SIMULADOR: Enviando " + type + " = " + value + " desde " + deviceId);
            vitalSignService.saveVitalSign(request);

        } catch (Exception e) {
            System.err.println("Error en simulador normal: " + e.getMessage());
        }
    }

    // Simular valores críticos ocasionalmente (cada 2 minutos)
    @Scheduled(fixedRate = 30000)
    public void simulateCriticalVitalSigns() {
        try {
            // Aumentar probabilidad a 80% para generar más críticos
            if (random.nextDouble() < 0.8) {
                String deviceId = getRandomDevice();
                String type = getRandomVitalSignType();
                BigDecimal criticalValue = generateCriticalValue(type);

                VitalSignRequestDTO request = new VitalSignRequestDTO();
                request.setDeviceId(deviceId);
                request.setType(type);
                request.setValue(criticalValue);
                request.setTimestamp(LocalDateTime.now());

                System.out.println("🚨 SIMULADOR CRÍTICO: " + type + " = " + criticalValue + " desde " + deviceId);
                vitalSignService.saveVitalSign(request);
            }
        } catch (Exception e) {
            System.err.println("Error en simulador crítico: " + e.getMessage());
        }
    }

    // Simular dispositivos con múltiples lecturas (cada minuto)
    @Scheduled(fixedRate = 60000)
    public void simulateBatchVitalSigns() {
        try {
            String deviceId = getRandomDevice();

            // Enviar múltiples signos vitales del mismo dispositivo
            for (String type : vitalSignTypes) {
                BigDecimal value = generateNormalValue(type);

                VitalSignRequestDTO request = new VitalSignRequestDTO();
                request.setDeviceId(deviceId);
                request.setType(type);
                request.setValue(value);
                request.setTimestamp(LocalDateTime.now());

                vitalSignService.saveVitalSign(request);
            }

            System.out.println("SIMULADOR BATCH: Enviados todos los signos vitales desde " + deviceId);

        } catch (Exception e) {
            System.err.println("Error en simulador batch: " + e.getMessage());
        }
    }

    private String getRandomDevice() {
        return deviceIds.get(random.nextInt(deviceIds.size()));
    }

    private String getRandomVitalSignType() {
        return vitalSignTypes.get(random.nextInt(vitalSignTypes.size()));
    }

    private BigDecimal generateNormalValue(String type) {
        return switch (type) {
            case "HEART_RATE" -> {
                // Rango normal: 60-100 bpm
                int value = 60 + random.nextInt(41); // 60-100
                yield new BigDecimal(value);
            }
            case "OXYGEN" -> {
                // Rango normal: 95-100%
                int value = 95 + random.nextInt(6); // 95-100
                yield new BigDecimal(value);
            }
            case "BLOOD_PRESSURE" -> {
                // Rango normal sistólica: 90-140 mmHg
                int value = 90 + random.nextInt(51); // 90-140
                yield new BigDecimal(value);
            }
            default -> new BigDecimal(100);
        };
    }

    private BigDecimal generateCriticalValue(String type) {
        return switch (type) {
            case "HEART_RATE" -> {
                // Valores críticos: <40 o >140 - Aumentar rangos
                boolean isHigh = random.nextBoolean();
                if (isHigh) {
                    int value = 145 + random.nextInt(55); // 145-200 (más críticos altos)
                    yield new BigDecimal(value);
                } else {
                    int value = 20 + random.nextInt(20); // 20-39 (más críticos bajos)
                    yield new BigDecimal(value);
                }
            }
            case "OXYGEN" -> {
                // Valor crítico: <90% - Ampliar rango crítico
                int value = 60 + random.nextInt(30); // 60-89 (más variedad crítica)
                yield new BigDecimal(value);
            }
            case "BLOOD_PRESSURE" -> {
                // Valor crítico: >180 mmHg - Aumentar rangos
                int value = 185 + random.nextInt(50); // 185-235 (más críticos)
                yield new BigDecimal(value);
            }
            default -> new BigDecimal(200);
        };
    }

    // Método manual para generar datos de prueba específicos
    public void generateTestData() {
        System.out.println("GENERANDO DATOS DE PRUEBA...");

        // Datos críticos específicos para testing
        String[] testDevices = {"TEST-001", "TEST-002", "TEST-003"};
        Object[][] testData = {
            {"HEART_RATE", new BigDecimal("155")}, // Crítico alto
            {"HEART_RATE", new BigDecimal("35")},  // Crítico bajo
            {"OXYGEN", new BigDecimal("85")},      // Crítico bajo
            {"BLOOD_PRESSURE", new BigDecimal("190")} // Crítico alto
        };

        for (String device : testDevices) {
            for (Object[] data : testData) {
                try {
                    VitalSignRequestDTO request = new VitalSignRequestDTO();
                    request.setDeviceId(device);
                    request.setType((String) data[0]);
                    request.setValue((BigDecimal) data[1]);
                    request.setTimestamp(LocalDateTime.now());

                    vitalSignService.saveVitalSign(request);
                    Thread.sleep(100); // Pausa pequeña entre envíos
                } catch (Exception e) {
                    System.err.println("Error generando dato de prueba: " + e.getMessage());
                }
            }
        }

        System.out.println("DATOS DE PRUEBA GENERADOS");
    }
}
