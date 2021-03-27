package sia.tacocloud.domain.model.resource;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import sia.tacocloud.domain.entity.Ingredient;
import sia.tacocloud.domain.entity.Ingredient.Type;
import sia.tacocloud.web.controllers.api.IngredientApiController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Getter
@Relation(value = "ingredient", collectionRelation = "ingredients")
public class IngredientResourceModel extends RepresentationModel {
    
    private String name;

    private Type type;

    public IngredientResourceModel(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
        this.addLinks(ingredient.getId());
    }

    private void addLinks(String id) {
        super.add(linkTo(methodOn(IngredientApiController.class).getById(id)).withSelfRel());
    }
}
