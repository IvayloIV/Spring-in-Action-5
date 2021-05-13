package sia.tacocloud.config.channels;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import lombok.extern.slf4j.Slf4j;

@Profile("queue-channel")
@Slf4j
@Configuration
public class QueueChannelConfig {
    
    @Bean
    @ServiceActivator(inputChannel = "textInChannel", poller = @Poller(fixedRate = "10000"))
    public MessageHandler textWriter() {
        return m -> log.info(m.getPayload().toString());
    }

    @Bean
    public MessageChannel textInChannel() {
        return new QueueChannel();
    }
}
