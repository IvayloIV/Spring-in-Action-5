package sia.tacocloud.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public Long id;

    @NotBlank(message = "Name is required.")
    public String deliveryName;

    @NotBlank(message = "Street is required.")
    public String street;

    @NotBlank(message = "Address is required.")
    public String address;

    public String city;

    @CreditCardNumber(message = "Credit card number is invalid.")
    public String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
             message = "Expiration should be in MM/YY format.")
    public String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "CCV should be exactly 3 symbols.")
    public String ccCCV;

    private Date placedAt;

    private List<Taco> tacos = new ArrayList();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
