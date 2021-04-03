package sia.tacocloud.web.controllers.api.consumers;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.client.Traverson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sia.tacocloud.domain.Taco;

@RestController
@RequestMapping(path = "/taco/consumer", produces = "application/json")
@CrossOrigin(origins = "*")
public class TacoApiConsumerController {
    
    private final Traverson traverson;

    public TacoApiConsumerController(Traverson traverson) {
        this.traverson = traverson;
    }

    @GetMapping(path = "/recent")
    public Collection<Taco> recentTacos() {
        Taco[] tacos = this.traverson
            .follow("tacos", "recents")
            // .follow("recents")
            .toObject(Taco[].class);

        return Arrays.stream(tacos).collect(Collectors.toList());
    }
}
