package sia.tacocloud.config.transformers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class UpperCaseTransformer {
    
    @Bean
    @Transformer(inputChannel = "upperCaseChannelIn", outputChannel = "upperCaseChannelOut")
    public GenericTransformer<String, String> toUpperCase() {
        return t -> t.toUpperCase();
    }

    @Bean
    public MessageChannel upperCaseChannelOut() {
        return new DirectChannel();
    }
}
