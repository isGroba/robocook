package com.cocina.robocook.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
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
}
