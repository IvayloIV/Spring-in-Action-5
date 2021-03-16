package sia.tacocloud.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taco {
    
    private Long id;
    
    @NotNull
    @Size(min = 3, message = "Name should be more then 2 symbols.")
    private String name;

    @Size(min = 1, message = "Select at least one ingredient.")
    private List<String> ingredients;

    private Date createdAt;
}
