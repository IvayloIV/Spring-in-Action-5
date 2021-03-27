package sia.tacocloud.web.controllers;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.model.binding.RegistrationForm;
import sia.tacocloud.domain.entity.User;
import sia.tacocloud.repositories.UserRepository;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegistrationController(PasswordEncoder passwordEncoder,
                                    UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm registrationFrom() {
        return new RegistrationForm();
    }

    @GetMapping
    public String showRegisterForm() {
        return "user/register";
    }
    
    @PostMapping
    public String processRegistration(@Valid RegistrationForm registrationFrom, Errors errors) {
        log.info(registrationFrom.toString());
        if (errors.hasErrors()) {
            return "user/register";
        }

        User user = registrationFrom.toUser(this.passwordEncoder);
        this.userRepository.save(user);
        return "redirect:/login";
    }
}
