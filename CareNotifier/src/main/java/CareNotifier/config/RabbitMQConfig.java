package CareNotifier.config;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue criticalAlertsQueue() {
        return new Queue("critical.alerts.queue", true);
    }

    @Bean
    public Queue warningAlertsQueue() {
        return new Queue("warning.alerts.queue", true);
    }

    @Bean
    public Queue infoAlertsQueue() {
        return new Queue("info.alerts.queue", true);
    }

    @Bean
    public Queue systemEventsQueue() {
        return new Queue("system.events.queue", true);
    }
}