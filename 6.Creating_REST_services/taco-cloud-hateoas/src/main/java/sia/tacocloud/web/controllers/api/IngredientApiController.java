package sia.tacocloud.web.controllers.api;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sia.tacocloud.domain.entity.Ingredient;
import sia.tacocloud.repositories.IngredientRepository;

@RestController
@RequestMapping(path = "/ingredient", produces = "application/json")
public class IngredientApiController {
    
    private final IngredientRepository ingredientRepository;

    public IngredientApiController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping(path = "/details/{id}")
    public ResponseEntity<Ingredient> getById(@PathVariable("id") String id) {
        Optional<Ingredient> ingredient = this.ingredientRepository.findById(id);

        if (ingredient.isPresent()) {
            return ResponseEntity.ok(ingredient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
