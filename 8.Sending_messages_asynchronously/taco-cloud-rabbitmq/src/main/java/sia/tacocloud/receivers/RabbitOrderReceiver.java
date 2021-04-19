package sia.tacocloud.receivers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Order;

@Slf4j
@Component
public class RabbitOrderReceiver {
    
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = rabbitTemplate.getMessageConverter();
    }

    public void receiveOrderWithoutTimeout() { // remove default timeout in yml
        Message message = this.rabbitTemplate.receive("tacocloud.order.queue");

        if (message == null) {
            log.info("We does not found message!");
        } else {
            Order order = (Order) this.messageConverter.fromMessage(message);
            log.info(order.getId().toString());
        }
    }

    public void receiveOrderWithTimeout() {
        Message message = this.rabbitTemplate.receive("tacocloud.order.queue", 30000);

        if (message == null) {
            log.info("We does not found message!");
        } else {
            Order order = (Order) this.messageConverter.fromMessage(message);
            log.info(order.getId().toString());
        }
    }

    public void receiveOrderWithMessageHeader() {
        Message message = this.rabbitTemplate.receive();

        if (message == null) {
            log.info("We does not found message!");
        } else {
            log.info(message.getMessageProperties().getHeader("X_ORDER_SOURCE"));
        }
    }

    public void receiveOrderWithAutoConverter() {
        Order order = (Order) this.rabbitTemplate.receiveAndConvert();
        log.info(order == null ? "We does not found message!" : order.getId().toString());
    }

    public void receiveOrderWithAutoConverterWithoutCast() {
        ParameterizedTypeReference<Order> parameterizedTypeReference = new ParameterizedTypeReference<Order>() {};
        Order order = this.rabbitTemplate.receiveAndConvert(parameterizedTypeReference); // Jackson2JsonMessageConverter must be used as a converter
        log.info(order == null ? "We does not found message!" : order.getId().toString());
    }

}
