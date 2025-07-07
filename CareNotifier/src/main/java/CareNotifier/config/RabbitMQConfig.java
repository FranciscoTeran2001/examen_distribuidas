package CareNotifier.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue colaNotificacionesEmergencia() {
        return QueueBuilder.durable("notificaciones.emergencia").build();
    }

    @Bean
    public Queue colaNotificacionesNormales() {
        return QueueBuilder.durable("notificaciones.normales").build();
    }

    @Bean
    public DirectExchange notificacionesExchange() {
        return new DirectExchange("notificaciones.exchange");
    }
}