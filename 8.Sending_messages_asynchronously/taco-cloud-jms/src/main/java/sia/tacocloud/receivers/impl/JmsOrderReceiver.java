package sia.tacocloud.receivers.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Order;
import sia.tacocloud.receivers.OrderReceiver;

@Slf4j
@Component
public class JmsOrderReceiver implements OrderReceiver {
    
    private final JmsTemplate jmsTemplate;
    private final Destination destination;
    private final MessageConverter messageConverter;

    public JmsOrderReceiver(JmsTemplate jmsTemplate,
                            Destination destination,
                            MessageConverter messageConverter) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.messageConverter = messageConverter;
    }

    public void receiveOrderPull() throws MessageConversionException, JMSException {
        Message message = this.jmsTemplate.receive();
        Order order = (Order) this.messageConverter.fromMessage(message);
        log.info(order.getId().toString());
    }

    public void receiveOrderPullWithDestination() throws MessageConversionException, JMSException {
        Message message = this.jmsTemplate.receive(this.destination);
        Order order = (Order) this.messageConverter.fromMessage(message);
        log.info(order.getId().toString());
    }

    public void receiveOrderPullWithPlainDestination() throws MessageConversionException, JMSException {
        Message message = this.jmsTemplate.receive("tacocloud.order.queue");
        Order order = (Order) this.messageConverter.fromMessage(message);
        log.info(order.getId().toString());
    }

    public void receiveOrderPullConverter() {
        Order order = (Order) this.jmsTemplate.receiveAndConvert();
        log.info(order.getId().toString());
    }

    public void receiveOrderPullWithDestinationConverter() {
        Order order = (Order) this.jmsTemplate.receiveAndConvert(this.destination);
        log.info(order.getId().toString());
    }

    public void receiveOrderPullWithPlainDestinationConverter() {
        Order order = (Order) this.jmsTemplate.receiveAndConvert("tacocloud.order.queue");
        log.info(order.getId().toString());
    }
}
