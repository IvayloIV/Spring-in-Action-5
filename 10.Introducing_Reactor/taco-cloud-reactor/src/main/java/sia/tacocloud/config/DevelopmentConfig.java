package sia.tacocloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Slf4j
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> log.info("Application start from here!!!");
    }
}
