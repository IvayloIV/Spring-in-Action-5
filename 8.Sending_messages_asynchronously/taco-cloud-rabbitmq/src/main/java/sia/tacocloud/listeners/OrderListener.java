package sia.tacocloud.listeners;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Order;

@Slf4j
@Component
public class OrderListener {
    
    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        log.info(order.getId().toString());
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(Message message) {
        MessageProperties messageProperties = message.getMessageProperties();
        log.info(messageProperties.getHeader("X_ORDER_SOURCE"));
    }
}
