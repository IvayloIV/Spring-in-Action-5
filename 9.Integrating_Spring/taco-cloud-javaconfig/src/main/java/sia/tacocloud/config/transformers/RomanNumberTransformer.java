package sia.tacocloud.config.transformers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.GenericTransformer;

import sia.tacocloud.utils.RomanNumbers;

@Configuration
public class RomanNumberTransformer {

    @Bean
    @Transformer(inputChannel = "romanNumberChannel", outputChannel = "genericMessageChannel")
    public GenericTransformer<Integer, String> transformToRomanNumber() {
        return RomanNumbers::toRoman;
    }
}
