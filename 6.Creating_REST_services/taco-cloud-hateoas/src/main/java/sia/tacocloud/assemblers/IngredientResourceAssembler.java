package sia.tacocloud.assemblers;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import sia.tacocloud.domain.entity.Ingredient;
import sia.tacocloud.domain.model.resource.IngredientResourceModel;
import sia.tacocloud.web.controllers.api.IngredientApiController;

@Component
public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResourceModel> {

    public IngredientResourceAssembler() {
        super(IngredientApiController.class, IngredientResourceModel.class);
    }

    @Override
    protected IngredientResourceModel instantiateModel(Ingredient ingredient) {
		return new IngredientResourceModel(ingredient);
	}

    @Override
    public IngredientResourceModel toModel(Ingredient ingredient) {
        return instantiateModel(ingredient);
    }

}
