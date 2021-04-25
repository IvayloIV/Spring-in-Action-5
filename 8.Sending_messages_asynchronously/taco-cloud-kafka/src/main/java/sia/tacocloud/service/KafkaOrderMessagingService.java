package sia.tacocloud.service;

import java.time.Instant;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import sia.tacocloud.domain.Order;

@Service
public class KafkaOrderMessagingService {
    
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public KafkaOrderMessagingService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        this.kafkaTemplate.send("tacocloud.orders.topic", order);
    }

    public void sendOrderWithDefaultTopic(Order order) {
        this.kafkaTemplate.sendDefault(order); //send to spring.kafka.template.default-topic topic
    }

    public void sendOrderWithKey(Order order) {
        this.kafkaTemplate.sendDefault("Order key", order);
    }

    public void sendOrderWithPartition(Order order) {
        this.kafkaTemplate.sendDefault(0, "Order key", order);
    }

    public void sendOrderWithTimestamp(Order order) {
        this.kafkaTemplate.sendDefault(0, Instant.now().getEpochSecond(), "Order key", order);
    }
}
