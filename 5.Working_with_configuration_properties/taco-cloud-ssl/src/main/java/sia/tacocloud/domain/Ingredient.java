package sia.tacocloud.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@NamedQuery(name = "Ingredient.themByIdAndName",
    query = "Select i from Ingredient i where i.id = :id and i.name = :name")
@NamedQuery(name = "Ingredient.hopByName",
    query = "Select i from Ingredient i where i.name = :name")
public class Ingredient {
    
    @Id
    private final String id;

    private final String name;

    @Enumerated(EnumType.STRING)
    private final Type type;

    public enum Type {
        FRUIT, CHEESE, MEAT, PATATOES
    }
}
