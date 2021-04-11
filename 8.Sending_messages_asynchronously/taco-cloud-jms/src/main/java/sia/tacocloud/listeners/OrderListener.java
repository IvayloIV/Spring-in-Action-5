package sia.tacocloud.listeners;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Order;

@Slf4j
@Component
public class OrderListener {
    
    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        log.info(order.getId().toString());
    }

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveMessage(Message message) throws JMSException {
        log.info(message.getStringProperty("X_ORDER_SOURCE"));
    }
}
