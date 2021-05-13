package sia.tacocloud.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {
    
    public void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String data);
}
