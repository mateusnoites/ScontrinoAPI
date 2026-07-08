package com.mateus.scontrinoapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Scontrino API",
                version = "1.0",
                description = "API de controle de gastos pessoais"
        )
)
@SpringBootApplication
public class ScontrinoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScontrinoApiApplication.class, args);
    }

}
