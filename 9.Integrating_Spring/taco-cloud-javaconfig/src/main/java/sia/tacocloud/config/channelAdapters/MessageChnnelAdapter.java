package sia.tacocloud.config.channelAdapters;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

@Configuration
public class MessageChnnelAdapter {
    
    @Bean
    @InboundChannelAdapter(poller = @Poller(fixedRate = "1000"), channel = "romanNumberChannel")
    public MessageSource<Integer> numberSource(AtomicInteger atomicInteger) {
        return () -> new GenericMessage<>(atomicInteger.incrementAndGet());
    }

    @Bean
    public AtomicInteger atomicInteger() {
        return new AtomicInteger();
    }
}
