package sia.tacocloud.assemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import sia.tacocloud.domain.entity.Taco;
import sia.tacocloud.domain.model.resource.IngredientResourceModel;
import sia.tacocloud.domain.model.resource.TacoResourceModel;
import sia.tacocloud.web.controllers.api.TacoApiController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResourceModel> {

    @Autowired
    private IngredientResourceAssembler ingredientResourceAssembler;

    public TacoResourceAssembler() {
        super(TacoApiController.class, TacoResourceModel.class);
    }

    @Override
    protected TacoResourceModel instantiateModel(Taco taco) {
		TacoResourceModel tacoResourceModel = new TacoResourceModel(taco);

        CollectionModel<IngredientResourceModel> collectionModel = this.ingredientResourceAssembler.toCollectionModel(taco.getIngredients());
        List<IngredientResourceModel> ingredients = StreamSupport.stream(collectionModel.spliterator(), false).collect(Collectors.toList());
        tacoResourceModel.setIngredients(ingredients);

        return tacoResourceModel;
	}

    @Override
    public TacoResourceModel toModel(Taco taco) {
        return createModelWithId(taco.getId(), taco);
    }
    
    @Override
    public CollectionModel<TacoResourceModel> toCollectionModel(Iterable<? extends Taco> tacos) {
        CollectionModel<TacoResourceModel> tacosModel = super.toCollectionModel(tacos);
        tacosModel.add(linkTo(methodOn(TacoApiController.class).recentTacos(null)).withRel("recent"));
        return tacosModel;
    }
}
