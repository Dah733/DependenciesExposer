package fr.adias.dependenciesexposer.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin("*")
@RequestMapping("/dependencies")
public class DependencyController {

    private final ResourceLoader resourceLoader;

    public DependencyController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public ResponseEntity<String> getDependencies() throws IOException {
        try{
        Resource resource = resourceLoader.getResource("file:dependencies.json");
        Path filePath = resource.getFile().toPath();
        byte[] fileBytes = Files.readAllBytes(filePath);
        String jsonContent = new String(fileBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(jsonContent, headers, HttpStatus.OK);
        }catch(IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}