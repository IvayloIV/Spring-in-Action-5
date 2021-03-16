package sia.tacocloud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sia.tacocloud.domain.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    public List<Ingredient> findIngredientByNameEqualsIgnoringCaseOrderByIdDesc(String name);

    @Query("Select i from Ingredient i where i.name = :name")
    public List<Ingredient> findByNameIngredient(@Param("name") String name);

    public List<Ingredient> themByIdAndName(@Param("id") String id, @Param("name") String name);

    public List<Ingredient> hopByName(@Param("name") String name);
}
