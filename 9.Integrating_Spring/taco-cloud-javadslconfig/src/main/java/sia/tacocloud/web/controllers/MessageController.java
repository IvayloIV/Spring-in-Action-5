package sia.tacocloud.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.domain.Taco;
import sia.tacocloud.gateways.MessageGateway;
import sia.tacocloud.gateways.UpperCaseGateway;
import sia.tacocloud.repositories.TacoRepository;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageGateway messageGateway;
    private final UpperCaseGateway upperCaseGateway;
    private final TacoRepository tacoRepository;
    
    public MessageController(MessageGateway messageGateway,
                                UpperCaseGateway upperCaseGateway,
                                TacoRepository tacoRepository) {
        this.messageGateway = messageGateway;
        this.upperCaseGateway = upperCaseGateway;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/create")
    public void createMessage() {
        String upperText = this.upperCaseGateway.toUpperCase("Abrakadabra");
        log.info(upperText);
    }
}
