package sia.tacocloud.web.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "tacocloud.api")
public class ApiProperties {
    
    private String url;
}
