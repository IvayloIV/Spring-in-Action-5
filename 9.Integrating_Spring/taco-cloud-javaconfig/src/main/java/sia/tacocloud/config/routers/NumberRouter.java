package sia.tacocloud.config.routers;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

@Configuration
public class NumberRouter {
    
    @Bean
    @Router(inputChannel = "evenOddChannel")
    public AbstractMessageRouter evenOddRouter(MessageChannel textInChannel, MessageChannel romanNumberChannel) {
        return new AbstractMessageRouter() {

            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> arg0) {
                Integer num = (Integer) arg0.getPayload();

                if (num % 2 == 0) {
                    return Collections.singleton(romanNumberChannel);
                } else {
                    return Collections.singleton(textInChannel);
                }
            }
        };
    }
}
