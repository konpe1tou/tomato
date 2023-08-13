package com.example.tomato;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Project Tomato",
        description = "生活を便利にします",
        version = "v1.0.0"))
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}