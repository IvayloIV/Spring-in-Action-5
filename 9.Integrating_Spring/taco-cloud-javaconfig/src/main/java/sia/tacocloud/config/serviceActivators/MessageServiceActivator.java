package sia.tacocloud.config.serviceActivators;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MessageServiceActivator {
    
    @Bean
    @ServiceActivator(inputChannel = "messageChannel")
    public MessageHandler showMessageWithMessageHandler() {
        return m -> log.info(m.getPayload().toString());
    }

    @Bean
    @ServiceActivator(inputChannel = "genericChannel", outputChannel = "messageChannel")
    public GenericHandler<String> returnMessageWithGenericHandler() {
        return (payload, headers) -> payload + " Generic";
    }

    @Bean
    @ServiceActivator(inputChannel = "genericMessageChannel")
    public GenericHandler<String> showMessageWithGenericHandler() {
        return (payload, headers) -> {
            log.info(payload + " Generic Message");
            return null;
        };
    }
}
