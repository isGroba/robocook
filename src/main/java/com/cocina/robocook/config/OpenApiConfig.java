package com.cocina.robocook.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"}) // Solo en desarrollo y testing
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Robocook")
                        .version("1.0.0")
                        .description("Documentación da API - Solo para desarrollo")
                        .contact(new Contact()
                                .name("Equipo do Feixoal")
                                .email("feixoalhome@gmail.com"))
                        .license(new License()
                                .name("GNU General Public License v3.0")
                                .url("https://github.com/isGroba/robocook/blob/main/LICENSE")));

        /*
        // Configurar esquema de seguridad JWT
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Ingresa tu token JWT")))

                // Aplicar seguridad a todos los endpoints
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));

         */
    }

    @Bean
    public GroupedOpenApi onlyBakOffice() {
        return GroupedOpenApi.builder()
                .group("BackOffice Rest") // Nombre del grupo que saldrá en el desplegable de Swagger
                .packagesToScan("com.cocina.robocook.controller.mvc") // Paquete exclusivo de tu API
                .pathsToMatch("/backoffice/**") //filtrar por ruta si empiezan por /backoffice
                .build();
    }

    @Bean
    public GroupedOpenApi onlyAPI() {
        return GroupedOpenApi.builder()
                .group("API Rest") // Nombre del grupo que saldrá en el desplegable de Swagger
                .packagesToScan("com.cocina.robocook.controller.api") // Paquete exclusivo de tu API
                .pathsToMatch("/api/**") //filtrar por ruta si empiezan por /api
                .build();
    }

    @Bean
    public GroupedOpenApi completeAPI() {
        return GroupedOpenApi.builder()
                .group("Complete API data") // Nombre del grupo que saldrá en el desplegable de Swagger
                .packagesToScan("com.cocina.robocook.controller.api") // Paquete exclusivo de tu API
                .build();
    }
}
