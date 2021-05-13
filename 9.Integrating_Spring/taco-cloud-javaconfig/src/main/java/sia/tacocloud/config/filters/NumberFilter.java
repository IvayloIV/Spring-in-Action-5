package sia.tacocloud.config.filters;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;

@Configuration
public class NumberFilter {
    
    @Filter(inputChannel = "oneMessageChannel", outputChannel = "textInChannel")
    public boolean filterMessagesStartsWithOne(String message) {
        return message.startsWith("One");
    }
}
