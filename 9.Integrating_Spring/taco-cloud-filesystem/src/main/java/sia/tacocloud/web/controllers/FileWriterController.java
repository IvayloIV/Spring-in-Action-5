package sia.tacocloud.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sia.tacocloud.integration.FileWriterGateway;

@RestController
@RequestMapping("/file")
public class FileWriterController {

    private final FileWriterGateway fileWriterGateway;
    
    public FileWriterController(FileWriterGateway fileWriterGateway) {
        this.fileWriterGateway = fileWriterGateway;
    }

    @GetMapping("/create")
    public void createFile() {
        this.fileWriterGateway.writeToFile("test.txt", "Abrakadabra");
    }
}
