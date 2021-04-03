package sia.tacocloud.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import sia.tacocloud.domain.Taco;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${remote.taco-cloud.base-url}")
    private String baseRemoteApiUri;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("user/login");
    }

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>> tacoProcessor(EntityLinks links) {
        return new RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>() {
            @Override
            public PagedModel<EntityModel<Taco>> process(PagedModel<EntityModel<Taco>> model) {
                model.add(
                    links.linkFor(Taco.class)
                        .slash("recent")
                        .withRel("recents")
                );
                return model;
            }
        };
    }

    @Bean
    public Traverson traverson() {
        return new Traverson(URI.create(this.baseRemoteApiUri), MediaTypes.HAL_JSON);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
