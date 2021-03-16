package sia.tacocloud.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Order;
import sia.tacocloud.repositories.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @GetMapping("/current")
    public String showOrderForm(Model model) {
        return "order/create";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "order/create";
        }

        this.orderRepository.save(order);
        sessionStatus.setComplete();

        log.info(order.toString());
        return "redirect:/";
    }
}
