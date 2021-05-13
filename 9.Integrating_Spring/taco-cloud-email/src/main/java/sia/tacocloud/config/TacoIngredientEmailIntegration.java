package sia.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

import sia.tacocloud.config.transformers.EmailToIngredientTransformer;
import sia.tacocloud.handlers.IngredientSubmitMessageHandler;
import sia.tacocloud.web.props.EmailProperties;

@Configuration
public class TacoIngredientEmailIntegration {
    
    @Bean
    public IntegrationFlow tacoIngredientEmailFlow(EmailProperties emailProperties,
                                              EmailToIngredientTransformer emailToIngredientTransformer,
                                              IngredientSubmitMessageHandler ingredientSubmitMessageHandler) {
        return IntegrationFlows
            .from(Mail.imapInboundAdapter(emailProperties.getImapUrl())
                    .simpleContent(true),
                    c -> c.poller(Pollers.fixedDelay(emailProperties.getPollRate())))
            .transform(emailToIngredientTransformer)
            .handle(ingredientSubmitMessageHandler)
            .get();
    }
}
