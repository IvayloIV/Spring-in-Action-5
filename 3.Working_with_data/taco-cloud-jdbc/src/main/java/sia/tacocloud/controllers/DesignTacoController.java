package sia.tacocloud.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Ingredient;
import sia.tacocloud.domain.Order;
import sia.tacocloud.domain.Taco;
import sia.tacocloud.repositories.IngredientRepository;
import sia.tacocloud.repositories.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design/show")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    public DesignTacoController(
            IngredientRepository ingredientRepository,
            TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
    
    @GetMapping
    public String showDesignForm(Model model) {
        this.ingredientRepository.findAll().stream()
            .collect(Collectors.groupingBy((Ingredient i) -> i.getType(), Collectors.toList()))
            .forEach((t, i) -> model.addAttribute(t.name(), i));
        
        return "taco/create";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute("order") Order order) {
        if (errors.hasErrors()) {
            return "taco/create";
        }

        Taco savedTaco = this.tacoRepository.save(design);
        order.addTaco(savedTaco);

        log.info(design.toString());
        return "redirect:/order/current";
    }
}
