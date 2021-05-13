package sia.tacocloud.web.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "tacocloud.email")
public class EmailProperties {
    
    private String username;

    private String password;

    private String host;

    private Integer port;

    private String mailbox;

    private Integer pollRate;

    public String getImapUrl() {
        return String.format("imaps://%s:%s@%s:%d/%s", username, password, host, port, mailbox);
    }
}
