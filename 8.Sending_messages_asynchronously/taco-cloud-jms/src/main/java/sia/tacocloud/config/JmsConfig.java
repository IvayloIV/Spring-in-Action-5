package sia.tacocloud.config;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import sia.tacocloud.domain.Order;

@Configuration
public class JmsConfig {

    @Bean
    public Destination destination() {
        return new ActiveMQQueue("tacocloud.order.queue");
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        Map<String, Class<?>> convertMap = new HashMap<>();
        convertMap.put("order", Order.class);
        converter.setTypeIdMappings(convertMap);
        return converter;
    }
}
