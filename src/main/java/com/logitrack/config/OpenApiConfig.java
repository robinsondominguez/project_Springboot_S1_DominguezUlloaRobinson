package com.logitrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
                        .version("1.0"));
    }
}
