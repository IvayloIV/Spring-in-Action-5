package sia.tacocloud.domain.model.resource;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;
import sia.tacocloud.domain.entity.Taco;

@Getter
@Relation(value = "taco", collectionRelation = "tacos")
public class TacoResourceModel extends RepresentationModel {

    private String name;

    private Date createdAt;

    @Setter
    private List<IngredientResourceModel> ingredients;

    public TacoResourceModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
    }
}