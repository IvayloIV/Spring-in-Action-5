package sia.tacocloud.gateways;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "upperCaseChannelIn",
                    defaultReplyChannel = "upperCaseChannelOut")
public interface UpperCaseGateway {
    
    public String toUpperCase(String text);
}
