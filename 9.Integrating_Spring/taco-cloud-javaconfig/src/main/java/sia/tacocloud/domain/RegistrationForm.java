package sia.tacocloud.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationForm {
    
    @NotNull
    @Length(min = 4, message = "Username should be more than 3 symbols.")
    private String username;

    @NotNull
    @Length(min = 3, message = "Password should be more than 2 symbols.")
    private String password;

    @NotNull
    @NotBlank(message = "Address is required.")
    private String address;

    private String city;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(null, username, passwordEncoder.encode(password), address, city);
    }
}
