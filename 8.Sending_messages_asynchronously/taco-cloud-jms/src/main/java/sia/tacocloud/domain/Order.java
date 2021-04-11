package sia.tacocloud.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "Name is required.")
    @Column(name = "delivery_name")
    public String deliveryName;

    @NotBlank(message = "Street is required.")
    public String street;

    @NotBlank(message = "Address is required.")
    public String address;

    public String city;

    @CreditCardNumber(message = "Credit card number is invalid.")
    @Column(name = "cc_number")
    public String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
             message = "Expiration should be in MM/YY format.")
    @Column(name = "cc_expiration")
    public String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "CCV should be exactly 3 symbols.")
    @Column(name = "cc_ccv")
    public String ccCCV;

    @Column(name = "placed_at")
    private Date placedAt;

    @ManyToMany(targetEntity = Taco.class)
    @JoinTable(name = "Taco_Order_Tacos",
        joinColumns = @JoinColumn(name = "taco_order_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "taco_id", referencedColumnName = "id"))
    private List<Taco> tacos = new ArrayList();

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    @PrePersist
    private void prePersist() {
        this.placedAt = new Date();
    }

}
