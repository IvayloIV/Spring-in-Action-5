package sia.tacocloud.config.channels;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import lombok.extern.slf4j.Slf4j;

@Profile("publish-channel")
@Slf4j
@Configuration
public class PublishChannelConfig {

    @Bean
    public IntegrationFlow textWriterOne() {
        return IntegrationFlows
            .from(textInChannel())
            .handle(m -> log.info(m.getPayload().toString() + " One"))
            .get();
    }

    @Bean
    public IntegrationFlow textWriterTwo() {
        return IntegrationFlows
            .from(textInChannel())
            .handle(m -> log.info(m.getPayload().toString() + " Two"))
            .get();
    }

    @Bean
    public MessageChannel textInChannel() {
        return new PublishSubscribeChannel();
    }
}
