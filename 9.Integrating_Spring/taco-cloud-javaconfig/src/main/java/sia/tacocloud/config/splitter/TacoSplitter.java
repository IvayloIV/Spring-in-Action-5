package sia.tacocloud.config.splitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Splitter;

import sia.tacocloud.domain.Ingredient;
import sia.tacocloud.domain.Taco;

@Configuration
public class TacoSplitter {
    
    @Splitter(inputChannel = "tacoSplitChannel", outputChannel = "tacoRouterChannel")
    public Collection<Object> splitTaco(Taco taco) {
        List<Object> collection = new ArrayList<>();
        collection.add(taco.getId().intValue());
        collection.add(taco.getIngredients());

        return collection;
    }

    @Splitter(inputChannel = "ingredientsSplitChannel", outputChannel = "textInChannel")
    public List<String> splitTacoIngredients(List<Ingredient> ingredients) {
        return ingredients
            .stream()
            .map(i -> i.getName())
            .collect(Collectors.toList());
    }
}
