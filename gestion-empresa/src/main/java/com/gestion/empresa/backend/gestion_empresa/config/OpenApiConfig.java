package com.gestion.empresa.backend.gestion_empresa.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Author: gordillox
 * Created on: 18/10/24
 */

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gestion de empresa",
                version = "1.0.0",
                description = "Adminstración de empresa"
        )
)
public class OpenApiConfig {
}
