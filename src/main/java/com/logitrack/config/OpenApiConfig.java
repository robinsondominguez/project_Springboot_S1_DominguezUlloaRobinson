package com.logitrack.config;

import io.swagger.v3.oas.models.Components; // <-- Añadir esta importación
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement; // <-- Añadir esta importación
import io.swagger.v3.oas.models.security.SecurityScheme;    // <-- Añadir esta importación
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI logitrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LogiTrack API")
                        .description("API para gestión de bodegas y auditoría")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .name("BearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}