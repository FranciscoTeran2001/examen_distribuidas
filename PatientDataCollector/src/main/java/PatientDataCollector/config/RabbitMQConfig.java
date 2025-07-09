package PatientDataCollector.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue newVitalSignQueue(){
        return QueueBuilder.durable("new.vital.sign.queue").build();
    }

    @Bean
    public Queue criticalAlertQueue(){
        return QueueBuilder.durable("critical.alerts.queue").build();
    }

    @Bean
    public Queue deviceOfflineQueue(){
        return QueueBuilder.durable("device.offline.queue").build();
    }

    @Bean
    public Queue systemEventsQueue(){
        return QueueBuilder.durable("system.events.queue").build();
    }
}