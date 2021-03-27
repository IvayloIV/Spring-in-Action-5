package sia.tacocloud.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Taco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size(min = 3, message = "Name should be more then 2 symbols.")
    private String name;

    @Size(min = 1, message = "Select at least one ingredient.")
    @ManyToMany(targetEntity = Ingredient.class)
    @JoinTable(name = "Taco_Ingredients",
        joinColumns = @JoinColumn(name = "taco_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "ingredients_id", referencedColumnName = "id"))
    private List<Ingredient> ingredients;

    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = new Date();
    }
}
