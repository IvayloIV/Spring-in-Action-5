package sia.tacocloud.receivers;

import javax.jms.JMSException;

import org.springframework.jms.support.converter.MessageConversionException;

public interface OrderReceiver {

    public void receiveOrderPull() throws MessageConversionException, JMSException;

    public void receiveOrderPullWithDestination() throws MessageConversionException, JMSException;

    public void receiveOrderPullWithPlainDestination() throws MessageConversionException, JMSException;

    public void receiveOrderPullConverter();

    public void receiveOrderPullWithDestinationConverter();

    public void receiveOrderPullWithPlainDestinationConverter();
}
