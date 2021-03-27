package sia.tacocloud.web.props;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "taco.orders")
@Data
@Validated
@Component
public class OrderProps {
    
    @Min(value = 5, message = "Page size cannot be less than 5")
    @Max(value = 25, message = "Page size cannot be more than 25")
    private int pageSize = 20;

    private String pageSizeMessage = "Hi";
}
