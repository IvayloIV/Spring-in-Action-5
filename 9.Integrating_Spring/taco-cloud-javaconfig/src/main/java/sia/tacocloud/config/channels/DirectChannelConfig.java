package sia.tacocloud.config.channels;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import lombok.extern.slf4j.Slf4j;

@Profile("direct-channel")
@Slf4j
@Configuration
public class DirectChannelConfig {
    
    @Bean
    @ServiceActivator(inputChannel = "textInChannel")
    public MessageHandler textWriter() {
        return m -> log.info(m.getPayload().toString());
    }

    @Bean
    public MessageChannel textInChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel romanNumberChannel() {
        return new DirectChannel();
    }
}
