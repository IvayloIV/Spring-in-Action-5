package sia.tacocloud.repositories;

import java.util.List;

import sia.tacocloud.domain.Ingredient;

public interface IngredientRepository {
    
    public List<Ingredient> findAll();

    public Ingredient findOne(String id);

    public Ingredient save(Ingredient ingredient);
}
