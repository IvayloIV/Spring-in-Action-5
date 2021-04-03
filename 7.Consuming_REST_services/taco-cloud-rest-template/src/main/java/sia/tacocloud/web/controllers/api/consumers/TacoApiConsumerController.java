package sia.tacocloud.web.controllers.api.consumers;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Taco;
import sia.tacocloud.repositories.TacoRepository;

@Slf4j
@RestController
@RequestMapping(path = "/taco/consumer", produces = "application/json")
@CrossOrigin(origins = "*")
public class TacoApiConsumerController {
    
    @Value("${remote.taco-cloud.base-url}")
    private String REMOTE_REST_BASE_URL;

    private final RestTemplate restTemplate;
    private final TacoRepository tacoRepository;

    public TacoApiConsumerController(RestTemplate restTemplate, TacoRepository tacoRepository) {
        this.restTemplate = restTemplate;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping(path = "recent/list")
    public List<Taco> recentList() {
        Taco[] tacos = this.restTemplate.getForObject(REMOTE_REST_BASE_URL + "taco/recent", Taco[].class);
        return Arrays.stream(tacos).collect(Collectors.toList());
    }

    @GetMapping(path = "/list/{id}")
    public Taco getByIdList(@PathVariable("id") Long id) {
        return this.restTemplate.getForObject(REMOTE_REST_BASE_URL + "taco/{id}", Taco.class, id);
    }

    @GetMapping(path = "/map/{id}")
    public Taco getByIdMap(@PathVariable("id") Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        return this.restTemplate.getForObject(REMOTE_REST_BASE_URL + "taco/{id}", Taco.class, params);
    }

    @GetMapping(path = "/uri/{id}")
    public Taco getByIdUri(@PathVariable("id") Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        URI uri = UriComponentsBuilder
            .fromHttpUrl(REMOTE_REST_BASE_URL + "taco/{id}")
            .build(params);
            
        return this.restTemplate.getForObject(uri, Taco.class);
    }

    @GetMapping(path = "/entity/list/{id}")
    public Taco getEntityByIdList(@PathVariable("id") Long id) {
        ResponseEntity<Taco> response = this.restTemplate.getForEntity(REMOTE_REST_BASE_URL + "taco/{id}", Taco.class, id);
        log.info(Instant.ofEpochMilli(response.getHeaders().getDate()).toString());
        return response.getBody();
    }

    @GetMapping(path = "/entity/map/{id}")
    public ResponseEntity<Taco> getEntityByIdMap(@PathVariable("id") Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        return this.restTemplate.getForEntity(REMOTE_REST_BASE_URL + "taco/{id}", Taco.class, params);
    }

    @GetMapping(path = "/entity/uri/{id}")
    public ResponseEntity<Taco> getEntityByIdUri(@PathVariable("id") Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        URI uri = UriComponentsBuilder
            .fromHttpUrl(REMOTE_REST_BASE_URL + "taco/{id}")
            .build(params);
            
        return this.restTemplate.getForEntity(uri, Taco.class);
    }

    @PutMapping(path = "/edit/link/{id}")
    public ResponseEntity<Taco> editTacoLink(@PathVariable("id") Long id, @RequestBody Taco newTaco) {
        Optional<Taco> tacoOptional = this.tacoRepository.findById(id);

        if (!tacoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Taco taco = tacoOptional.get();
        taco.setName(newTaco.getName());
        log.info(newTaco.getName());
        this.restTemplate.put(REMOTE_REST_BASE_URL + "taco/edit", taco);
        return ResponseEntity.ok(taco);
    }

    @PutMapping(path = "/edit/uri/{id}")
    public ResponseEntity<Taco> editTacoUri(@PathVariable("id") Long id, @RequestBody Taco newTaco) {
        Optional<Taco> tacoOptional = this.tacoRepository.findById(id);

        if (!tacoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Taco taco = tacoOptional.get();
        taco.setName(newTaco.getName());
        
        URI uri = UriComponentsBuilder
            .fromHttpUrl(REMOTE_REST_BASE_URL + "taco/edit")
            .build()
            .toUri();

        this.restTemplate.put(uri, taco);
        return ResponseEntity.ok(taco);
    }

    @DeleteMapping(path = "/delete/link/{id}")
    public void deleteTacoList(@PathVariable("id") Long id) {
        this.restTemplate.delete(REMOTE_REST_BASE_URL + "taco/delete/{id}", id);
    }

    @DeleteMapping(path = "/delete/map/{id}")
    public void deleteTacoMap(@PathVariable("id") Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        this.restTemplate.delete(REMOTE_REST_BASE_URL + "taco/delete/{id}", params);
    }

    @DeleteMapping(path = "/delete/uri/{id}")
    public void deleteTacoUri(@PathVariable("id") Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        URI uri = UriComponentsBuilder
            .fromHttpUrl(REMOTE_REST_BASE_URL + "taco/delete/{id}")
            .build(params);
            
        this.restTemplate.delete(uri);
    }

    @PostMapping(path = "/create/object/list")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Taco createTacoObjectList(@RequestBody Taco taco) {
        return this.restTemplate.postForObject(REMOTE_REST_BASE_URL + "taco/create", taco, Taco.class);
    }

    @PostMapping(path = "/create/object/uri")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Taco createTacoObjectUri(@RequestBody Taco taco) {
        URI uri = UriComponentsBuilder
            .fromHttpUrl(REMOTE_REST_BASE_URL + "taco/create")
            .build()
            .toUri();

        return this.restTemplate.postForObject(uri, taco, Taco.class);
    }

    @PostMapping(path = "/create/location/list")
    @ResponseStatus(value = HttpStatus.CREATED)
    public URI createTacoLocationList(@RequestBody Taco taco) {
        return this.restTemplate.postForLocation(REMOTE_REST_BASE_URL + "taco/create", taco);
    }

    @PostMapping(path = "/create/location/uri")
    @ResponseStatus(value = HttpStatus.CREATED)
    public URI createTacoLocationUri(@RequestBody Taco taco) {
        URI uri = UriComponentsBuilder
            .fromHttpUrl(REMOTE_REST_BASE_URL + "taco/create")
            .build()
            .toUri();

        return this.restTemplate.postForLocation(uri, taco);
    }

    @PostMapping(path = "/create/entity/list")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Taco createTacoEntityList(@RequestBody Taco taco) {
        ResponseEntity<Taco> response = this.restTemplate.postForEntity(REMOTE_REST_BASE_URL + "taco/create", taco, Taco.class);
        log.info("New resource location: " + response.getHeaders().getLocation());
        return response.getBody();
    }

    @PostMapping(path = "/create/entity/uri")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Taco createTacoEntityUri(@RequestBody Taco taco) {
        URI uri = UriComponentsBuilder
            .fromHttpUrl(REMOTE_REST_BASE_URL + "taco/create")
            .build()
            .toUri();

        ResponseEntity<Taco> response = this.restTemplate.postForEntity(uri, taco, Taco.class);
        log.info("New resource location: " + response.getHeaders().getLocation());
        return response.getBody();
    }

    @GetMapping(path = "/list/entity/{id}")
    public Taco getByIdEntityList(@PathVariable("id") Long id) {
        ResponseEntity<Taco> response = this.restTemplate.exchange(REMOTE_REST_BASE_URL + "taco/{id}", HttpMethod.GET, null, Taco.class, id);
        return response.getBody();
    }

    @PostMapping(path = "/create/list/entity")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Taco createTacoListEntity(@RequestBody Taco taco) {
        HttpEntity<Taco> httpEntity = new HttpEntity<>(taco);
        ResponseEntity<Taco> response = this.restTemplate.exchange(REMOTE_REST_BASE_URL + "taco/create", HttpMethod.POST, httpEntity, Taco.class);
        log.info("New resource location: " + response.getHeaders().getLocation());
        return response.getBody();
    }
}
