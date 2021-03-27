package sia.tacocloud.web.controllers.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sia.tacocloud.domain.entity.Taco;
import sia.tacocloud.assemblers.TacoResourceAssembler;
import sia.tacocloud.domain.model.resource.TacoResourceModel;
import sia.tacocloud.repositories.TacoRepository;

@Controller
@RequestMapping(path = "/taco", produces = { "application/json", "application/xml" })
@CrossOrigin(origins = "*")
public class TacoApiController {
    
    private final TacoRepository tacoRepository;
    private final TacoResourceAssembler tacoResourceAssembler;
    private final PagedResourcesAssembler<Taco> pagedResourcesAssembler;

    @Autowired
    public TacoApiController(
            TacoRepository tacoRepository,
            TacoResourceAssembler tacoResourceAssembler,
            PagedResourcesAssembler<Taco> pagedResourcesAssembler) {
        this.tacoRepository = tacoRepository;
        this.tacoResourceAssembler = tacoResourceAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(path = "/recent")
    @ResponseBody
    public PagedModel<TacoResourceModel> recentTacos(Pageable pageable) {
        Page<Taco> tacosPage = this.tacoRepository.findAll(pageable);
        
        // CollectionModel<EntityModel<Taco>> collectionModel = CollectionModel.wrap(tacos);
        // collectionModel.add(new Link("http://localhost:8080/taco/recent", "recent"));
        
        // Link linkRecent = WebMvcLinkBuilder.linkTo(TacoApiController.class)
        //     .slash("recent")
        //     .withRel("recent");
        // collectionModel.add(linkRecent);

        // Link linkRecent = linkTo(methodOn(TacoApiController.class).recentTacos()).withRel("recent");
        // collectionModel.add(linkRecent);

        return this.pagedResourcesAssembler.toModel(tacosPage, (RepresentationModelAssembler) this.tacoResourceAssembler);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Taco> getById(@PathVariable("id") Long id) {
        Optional<Taco> taco = this.tacoRepository.findById(id);

        if (taco.isPresent()) {
            return ResponseEntity.ok(taco.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/create", consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTaco(@RequestBody Taco taco) {
        this.tacoRepository.save(taco);
    }

    @PutMapping(path = "/edit", consumes = "application/json")
    @ResponseBody
    public Taco editTaco(@RequestBody Taco taco) {
        return this.tacoRepository.save(taco);
    }

    @PatchMapping(path = "/update/{id}", consumes = "application/json")
    public ResponseEntity<Taco> updateTaco(@PathVariable("id") Long id , @RequestBody Taco taco) {
        Optional<Taco> existedTacoOptional = this.tacoRepository.findById(id);

        if (!existedTacoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Taco existedTaco = existedTacoOptional.get();
        if (taco.getName() != null && !taco.getName().equals(null)) {
            existedTaco.setName(taco.getName());
        }

        this.tacoRepository.save(existedTaco);
        return ResponseEntity.ok(existedTaco);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Taco> deleteTaco(@PathVariable("id") Long id) {
        try {
            this.tacoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
