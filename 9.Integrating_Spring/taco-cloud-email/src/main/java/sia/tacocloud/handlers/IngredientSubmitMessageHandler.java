package sia.tacocloud.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Ingredient;
import sia.tacocloud.web.props.ApiProperties;

@Slf4j
@Component
public class IngredientSubmitMessageHandler implements GenericHandler<Ingredient> {

    private RestTemplate restTemplate;
    private ApiProperties apiProperties;

    public IngredientSubmitMessageHandler(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.restTemplate = restTemplate;
        this.apiProperties = apiProperties;
    }

    @Override
    public Object handle(Ingredient ingredient, MessageHeaders headers) {
        ResponseEntity<String> responseEntity = this.restTemplate
            .postForEntity(this.apiProperties.getUrl() + "/ingredient/create", ingredient, String.class);
        log.info(responseEntity.getBody());
        return null;
    }
    
}
