package sia.tacocloud.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import sia.tacocloud.domain.Order;

@Service
public class JmsOrderMessagingService {
    
    private final JmsTemplate jmsTemplate;
    private final Destination destination;
    private final MessageConverter messageConverter;

    public JmsOrderMessagingService(JmsTemplate jmsTemplate,
                                    Destination destination,
                                    MessageConverter messageConverter) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.messageConverter = messageConverter;
    }

    public void sendOrderAnonymous(Order order) {
        this.jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return messageConverter.toMessage(order, session);
            }
        });
    }

    public void sendOrderLambda(Order order) {
        this.jmsTemplate.send(s -> this.messageConverter.toMessage(order, s));
    }

    public void sendOrderWithDestination(Order order) {
        this.jmsTemplate.send(this.destination, s -> this.messageConverter.toMessage(order, s));
    }

    public void sendOrderWithPlainDestination(Order order) {
        this.jmsTemplate.send("tacocloud.order.queue", s -> this.messageConverter.toMessage(order, s));
    }

    public void sendOrderLambdaConverted(Order order) {
        this.jmsTemplate.convertAndSend(order);
    }

    public void sendOrderWithDestinationConverted(Order order) {
        this.jmsTemplate.convertAndSend(this.destination, order);
    }

    public void sendOrderWithPlainDestinationConverted(Order order) {
        this.jmsTemplate.convertAndSend("tacocloud.order.queue", order);
    }

    public void sendOrderWithProperty(Order order) {
        this.jmsTemplate.send(s -> {
            Message message = this.messageConverter.toMessage(order, s);
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }

    public void sendOrderWithPropertyProcessor(Order order) {
        this.jmsTemplate.convertAndSend(order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("X_ORDER_SOURCE", "WEB");
                return message;
            }
        });
    }

    public void sendOrderWithPropertyProcessorLambda(Order order) {
        this.jmsTemplate.convertAndSend(order, m -> {
            m.setStringProperty("X_ORDER_SOURCE", "WEB");
            return m;
        });
    }

    public void sendOrderWithPropertyProcessorReference(Order order) {
        this.jmsTemplate.convertAndSend(order, this::messagePostProcessor);
    }

    private Message messagePostProcessor(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }

}
