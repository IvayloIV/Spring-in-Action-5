package sia.tacocloud.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@NamedQuery(name = "Ingredient.themByIdAndName",
    query = "Select i from Ingredient i where i.id = :id and i.name = :name")
@NamedQuery(name = "Ingredient.hopByName",
    query = "Select i from Ingredient i where i.name = :name")
public class Ingredient {
    
    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        FRUIT, CHEESE, MEAT, PATATOES
    }
}
