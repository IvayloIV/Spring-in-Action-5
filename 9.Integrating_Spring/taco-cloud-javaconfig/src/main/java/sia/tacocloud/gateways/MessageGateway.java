package sia.tacocloud.gateways;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

import sia.tacocloud.domain.Taco;

@Component
@MessagingGateway(defaultRequestChannel = "romanNumberChannel")
public interface MessageGateway {
    
    public void sendMessage(String data);

    public void sendNumber(Integer data);

    public void sendTaco(Taco data);
}
