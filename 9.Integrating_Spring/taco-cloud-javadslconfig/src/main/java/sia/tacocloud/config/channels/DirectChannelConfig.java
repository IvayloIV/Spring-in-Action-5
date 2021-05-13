package sia.tacocloud.config.channels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Ingredient;
import sia.tacocloud.domain.Taco;
import sia.tacocloud.utils.RomanNumbers;

@Profile("direct-channel")
@Slf4j
@Configuration
public class DirectChannelConfig {
    
    @Profile("normal-flow")
    @Bean
    public IntegrationFlow textNormalWriter() {
        return IntegrationFlows
            .from(textInChannel())
            .handle(this::logMessage)
            .get();
    }

    @Profile("filter-flow")
    @Bean
    public IntegrationFlow textFilterWriter() {
        GenericSelector<String> genericSelector = new GenericSelector<String>(){
            @Override
            public boolean accept(String arg0) {
                return arg0.startsWith("One");
            }
        };

        GenericSelector<String> genericSelectorLambda = m -> m.startsWith("One");

        return IntegrationFlows
            .from(textInChannel())
            .<String>filter(genericSelectorLambda)
            .handle(this::logMessage)
            .get();
    }

    @Profile("transformer-flow")
    @Bean
    public IntegrationFlow textTransformerWriter() {
        return IntegrationFlows
            .from(textInChannel())
            .<Integer, String>transform(RomanNumbers::toRoman)
            .handle(this::logMessage)
            .get();
    }

    @Profile("router-flow")
    @Bean
    public IntegrationFlow textRouterWriter() {
        return IntegrationFlows
            .from(textInChannel())
            .<Integer, String>route(n -> n % 2 == 0 ? "EVEN" : "ODD", mapping -> mapping
                .subFlowMapping("EVEN", sf -> sf
                    .<Integer, String>transform(RomanNumbers::toRoman)
                    .handle(this::logMessage))
                .subFlowMapping("ODD", sf -> sf
                    .handle(this::logMessage)))
            .get();
    }

    @Profile("splitter-flow")
    @Bean
    public IntegrationFlow textSplitterWriter() {
        return IntegrationFlows
            .from(textInChannel())
            .<Taco>split(Taco.class, taco -> {
                List<Object> collection = new ArrayList<>();
                collection.add(taco.getId().intValue());
                collection.add(taco.getIngredients());
                return collection;
            })
            .<Object, String>route(o -> o instanceof Integer ? "TACO_ID" : "TACO_INGREDIENTS", mapping -> mapping
                .subFlowMapping("TACO_ID", sf -> sf
                    .<Integer, String>transform(RomanNumbers::toRoman)
                    .handle(this::logMessage))
                .subFlowMapping("TACO_INGREDIENTS", sf -> sf
                    .<List>split(List.class, ingredients -> {
                        return ((List<Ingredient>) ingredients)
                            .stream()
                            .map(i -> i.getName())
                            .collect(Collectors.toList());
                    })
                    .handle(this::logMessage)))
            .get();
    }

    @Profile("message-handler-flow")
    @Bean
    public IntegrationFlow textMessageHandler() {
        return IntegrationFlows
            .from(textInChannel())
            .handle(this::logMessage)
            .get();
    }

    @Profile("generic-handler-flow")
    @Bean
    public IntegrationFlow textGenericHandler() {
        return IntegrationFlows
            .from(textInChannel())
            .<String>handle((payload, headers) -> payload + " Generic")
            .handle(this::logMessage)
            .get();
    }

    @Profile("generic-handler-null-flow")
    @Bean
    public IntegrationFlow textGenericHandlerNull() {
        return IntegrationFlows
            .from(textInChannel())
            .<String>handle((payload, headers) -> {
                log.info(payload + " Generic null");
                return null;
            })
            .get();
    }

    @Profile("upper-case-flow")
    @Bean
    public IntegrationFlow upperCaseText() {
        return IntegrationFlows
            .from(textInChannel())
            .<String, String>transform(t -> t.toUpperCase())
            .channel(upperCaseChannelOut())
            .get();
    }

    @Profile("inbound-flow")
    @Bean
    public IntegrationFlow inboundTextChannel(AtomicInteger atomicInteger) {
        return IntegrationFlows
            .from(() -> new GenericMessage<>(atomicInteger.incrementAndGet()), 
                    c -> c.poller(Pollers.fixedRate(1000)))
            .<Integer, String>transform(RomanNumbers::toRoman)
            .handle(this::logMessage)
            .get();
    }

    @Bean
    public MessageChannel textInChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel romanNumberChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel upperCaseChannelOut() {
        return new DirectChannel();
    }

    @Bean
    public AtomicInteger atomicInteger() {
        return new AtomicInteger();
    }

    private void logMessage(Message<?> m) {
        log.info(m.getPayload().toString()); 
    }
}
