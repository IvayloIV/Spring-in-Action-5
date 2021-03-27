package sia.tacocloud.web.controllers.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sia.tacocloud.domain.entity.Order;
import sia.tacocloud.repositories.OrderRepository;

@RestController
@RequestMapping(path = "/order", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApiController {
    
    private final OrderRepository orderRepository;

    public OrderApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping(path = "/recent")
    public CollectionModel<EntityModel<Order>> recentOrders() {
        Iterable<Order> orders = this.orderRepository.findAll();
        
        CollectionModel<EntityModel<Order>> collectionModel = CollectionModel.wrap(orders);
        Link linkRecent = linkTo(methodOn(OrderApiController.class).recentOrders()).withRel("recent");
        collectionModel.add(linkRecent);

        return collectionModel;
    }
}
