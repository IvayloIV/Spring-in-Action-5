package sia.tacocloud.web.controllers;

import javax.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
import sia.tacocloud.domain.User;
import sia.tacocloud.repositories.OrderRepository;
import sia.tacocloud.repositories.UserRepository;
import sia.tacocloud.web.props.OrderProps;

@Slf4j
@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderProps orderProps;

    @Autowired
    public OrderController(OrderRepository orderRepository,
                            UserRepository userRepository,
                            OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String showOrderForm(@ModelAttribute Order order,
                                @AuthenticationPrincipal User user) {
        order.setAddress(user.getAddress());
        order.setCity(user.getCity());
        return "order/create";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute Order order, 
                                Errors errors, 
                                SessionStatus sessionStatus
                                /*Principal principal*/
                                /*Authentication authentication*/
                                /*@AuthenticationPrincipal User user*/) {
        if (errors.hasErrors()) {
            return "order/create";
        }

        // Variant 1: With Principal
        /*String name = principal.getName();
        User user = this.userRepository.findByUsername(name).orElse(null);*/

        // Variant 2: With Authentication
        /*User user = (User) authentication.getPrincipal();*/

        // Variant 3: With @AuthenticationPrincipal

        // Variant 4: With SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        order.setUser(user);
        log.info(order.toString());
        this.orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping("/user")
    public String showUserOrders(@AuthenticationPrincipal User user, Model model) {
        Pageable pabeable = PageRequest.of(0, this.orderProps.getPageSize());
        List<Order> orders = this.orderRepository.findByUserOrderByPlacedAtDesc(user, pabeable);
        model.addAttribute("orders", orders);
        model.addAttribute("pageSizeMessage", this.orderProps.getPageSizeMessage());
        return "order/user";
    }
}
