package sia.tacocloud.web.controllers.api.producers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import sia.tacocloud.domain.Taco;
import sia.tacocloud.repositories.TacoRepository;

@RepositoryRestController
public class TacoApiController {
    
    private final TacoRepository tacoRepository;

    @Autowired
    public TacoApiController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<Iterable<Taco>> recentTacos(Pageable pageable) {
        List<Taco> tacos = this.tacoRepository.findAll(pageable).getContent();
        return ResponseEntity.ok(tacos); // when we use RepositoryRestController, we must use ResponseEnitty or @ResponseBody
    }
}
