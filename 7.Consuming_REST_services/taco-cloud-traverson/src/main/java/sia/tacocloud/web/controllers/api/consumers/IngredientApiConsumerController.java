package sia.tacocloud.web.controllers.api.consumers;

import java.util.Collection;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sia.tacocloud.domain.Ingredient;

@RestController
@RequestMapping(path = "/ingredient/consumer", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientApiConsumerController {
    
    private Traverson traverson;
    private RestTemplate restTemplate;

    public IngredientApiConsumerController(Traverson traverson, RestTemplate restTemplate) {
        this.traverson = traverson;
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/list")
    public Collection<Ingredient> getAll() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> parameterizedTypeReference = 
            new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};

        CollectionModel<Ingredient> ingredients = this.traverson
            .follow("ingredients")
            .toObject(parameterizedTypeReference);

        return ingredients.getContent();
    }

    @PostMapping(path = "/create")
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        String ingredientHref = this.traverson
            .follow("ingredients")
            .asLink()
            .getHref();

        ResponseEntity<Ingredient> response = this.restTemplate.postForEntity(ingredientHref, 
            ingredient, 
            Ingredient.class);

        return response.getBody();
    }
}
