package com.example.mvcapiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@SpringBootApplication
public class MvcApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcApiServerApplication.class, args);
    }

    @GetMapping("/posts/{id}")
    public Map<String, String> getPosts(@PathVariable Long id) {
        return Map.of("id", id.toString(), "content", "Posts Content %d".formatted(id));
    }
}
