package sia.tacocloud.config.channels;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.messaging.MessageChannel;

import lombok.extern.slf4j.Slf4j;

@Profile("queue-channel")
@Slf4j
@Configuration
public class QueueChannelConfig {
    
    @Bean
    public IntegrationFlow textWriter() {
        return IntegrationFlows
            .from(textInChannel())
            .handle(m -> log.info(m.getPayload().toString()), c -> c.poller(Pollers.fixedRate(10000)))
            .get();
    }

    @Bean
    public MessageChannel textInChannel() {
        return new QueueChannel();
    }
}
