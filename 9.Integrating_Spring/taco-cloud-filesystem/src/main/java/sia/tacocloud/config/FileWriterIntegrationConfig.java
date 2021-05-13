package sia.tacocloud.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class FileWriterIntegrationConfig {

    // --- With XML Configuration ---

    @Profile("xmlconfig")
    @Configuration
    @ImportResource(locations = "classpath:/filewriter-config.xml")
    public static class XmlConfiguration {}

    // --- With Java Configuration ---

    @Profile("javaconfig")
    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return d -> d.toUpperCase();
    }

    @Profile("javaconfig")
    @Bean
    @InboundChannelAdapter(poller = @Poller(fixedRate = "1000"), channel = "fileWriterChannel")
    public MessageSource<File> fileReader() {
        FileReadingMessageSource fileReadingMessageSource = new FileReadingMessageSource();
        fileReadingMessageSource.setDirectory(new File("D://temp/temp2"));
        fileReadingMessageSource.setFilter(new SimplePatternFileListFilter("*.txt"));

        return fileReadingMessageSource;
    }

    @Profile("javaconfig")
    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler fileWritingMessageHandler =
            new FileWritingMessageHandler(new File("D://temp/"));

        fileWritingMessageHandler.setExpectReply(false);
        fileWritingMessageHandler.setFileExistsMode(FileExistsMode.APPEND);
        fileWritingMessageHandler.setAppendNewLine(true);

        return fileWritingMessageHandler;
    }

    // --- With Java DSL Configuration ---

    @Profile("javadslconfig")
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows.from(textInChannel()) // == MessageChannels.direct("textInChannel")
                .<String, String>transform(m -> m.toUpperCase())
                .channel(fileWriterChannel())
                .handle(Files
                    .outboundAdapter(new File("D://temp/"))
                    .fileExistsMode(FileExistsMode.APPEND)
                    .appendNewLine(true))
                .get();
    }

    @Profile("javadslconfig-reader")
    @Bean
    public IntegrationFlow fileReaderFlow() {
        return IntegrationFlows.from(Files
                    .inboundAdapter(new File("D://temp/temp2"))
                    .patternFilter("*.txt"), c -> c.poller(Pollers.fixedRate(1000)))
                .channel(fileWriterChannel())
                .handle(Files
                    .outboundAdapter(new File("D://temp/"))
                    .fileExistsMode(FileExistsMode.APPEND)
                    .appendNewLine(true))
                .get();
    }

    @Profile("!xmlconfig")
    @Bean
    public MessageChannel textInChannel() {
        return new DirectChannel();
    }

    @Profile("!xmlconfig")
    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }
}
