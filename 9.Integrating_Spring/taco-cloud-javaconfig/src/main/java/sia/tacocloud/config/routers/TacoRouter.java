package sia.tacocloud.config.routers;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.router.PayloadTypeRouter;

@Configuration
public class TacoRouter {
    
    @Bean
    @Router(inputChannel = "tacoRouterChannel")
    public MessageRouter splitTacoRouter() {
        PayloadTypeRouter payloadTypeRouter = new PayloadTypeRouter();
        payloadTypeRouter.setChannelMapping(Integer.class.getName(), "romanNumberChannel");
        payloadTypeRouter.setChannelMapping(List.class.getName(), "ingredientsSplitChannel");

        return payloadTypeRouter;
    }
}
