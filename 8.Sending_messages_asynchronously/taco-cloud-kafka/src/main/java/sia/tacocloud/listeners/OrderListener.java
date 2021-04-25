package sia.tacocloud.listeners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Order;

@Slf4j
@Component
public class OrderListener {
    
    @KafkaListener(topics = "tacocloud.orders.topic", groupId = "taco-group")
    public void handleOrderPlain(Order order) {
        log.info(order.getId().toString());
    }

    @KafkaListener(topics = "tacocloud.orders.topic", groupId = "taco-group")
    public void handleOrderWithConsumerRecord(ConsumerRecord<String, Order> record) {
        log.info("Partition - " + record.partition());
        log.info("Timestamp - " + record.timestamp());
        log.info("Key - " + record.key());
        log.info("Order id - " + record.value().getId());
    }

    @KafkaListener(topics = "tacocloud.orders.topic", groupId = "taco-group")
    public void handleOrderWithMessage(Message<Order> message) {
        MessageHeaders headers = message.getHeaders();
        log.info("Partition - " + headers.get(KafkaHeaders.RECEIVED_PARTITION_ID));
        log.info("Timestamp - " + headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
        log.info("Key - " + headers.get(KafkaHeaders.RECEIVED_MESSAGE_KEY));
        log.info("Order id - " + message.getPayload().getId());
    }
}
