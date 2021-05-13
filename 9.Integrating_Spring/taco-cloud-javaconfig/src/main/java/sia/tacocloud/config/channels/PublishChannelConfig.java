package sia.tacocloud.config.channels;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import lombok.extern.slf4j.Slf4j;

@Profile("publish-channel")
@Slf4j
@Configuration
public class PublishChannelConfig {

    @Bean
    @ServiceActivator(inputChannel = "textInChannel")
    public MessageHandler textWriterOne() {
        return m -> log.info(m.getPayload().toString() + " One");
    }

    @Bean
    @ServiceActivator(inputChannel = "textInChannel")
    public MessageHandler textWriterTwo() {
        return m -> log.info(m.getPayload().toString() + " Two");
    }

    @Bean
    public MessageChannel textInChannel() {
        return new PublishSubscribeChannel();
    }
}
