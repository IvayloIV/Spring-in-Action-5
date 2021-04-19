package sia.tacocloud.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import sia.tacocloud.domain.Order;

@Service
public class RabbitOrderMessagingService {
    
    private final RabbitTemplate rabbitTemplate;

    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;   
    }

    public void sendOrderWithPlainRoutingKey(Order order) {
        MessageConverter converter = this.rabbitTemplate.getMessageConverter();
        MessageProperties messageProperties = new MessageProperties();
        Message message = converter.toMessage(order, messageProperties);
        this.rabbitTemplate.send("tacocloud.order.queue", message);
    }

    public void sendOrderWithDefaultRoutingKey(Order order) {
        MessageConverter converter = this.rabbitTemplate.getMessageConverter();
        MessageProperties messageProperties = new MessageProperties();
        Message message = converter.toMessage(order, messageProperties);
        this.rabbitTemplate.send(message);
    }

    public void sendOrderWithAutoConverter(Order order) {
        this.rabbitTemplate.convertAndSend(order);
    }

    public void sendOrderWithMessageProperty(Order order) {
        MessageConverter converter = this.rabbitTemplate.getMessageConverter();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = converter.toMessage(order, messageProperties);
        this.rabbitTemplate.send(message);
    }

    public void sendOrderWithMessagePostProcessor(Order order) {
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor(){

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties = message.getMessageProperties();
                messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
                return message;
            }
        };
        this.rabbitTemplate.convertAndSend(order, messagePostProcessor);
    }

    public void sendOrderWithLambdaMessagePostProcessor(Order order) {
        this.rabbitTemplate.convertAndSend(order, m -> {
            MessageProperties messageProperties = m.getMessageProperties();
            messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
            return m;
        });
    }
}
