package sia.tacocloud.web.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sia.tacocloud.domain.Ingredient;
import sia.tacocloud.repositories.IngredientRepository;

@RestController
@RequestMapping(path = "/ingredient")
public class IngredientController {
    
    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createIngredient(@RequestBody Ingredient ingredient) {
        this.ingredientRepository.save(ingredient);
        return ResponseEntity.ok("Ingredient created.");
    }
}
