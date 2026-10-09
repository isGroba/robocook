# Robocook
Aplicación Backend para la gestión de recetas de cocina. 
***
### Arquitectura

1. Patrón DTO (Data Transfer Object)
2. Modelo Rest para API y MVC para gestión de backoffice
3. Separación de responsabilidades (SOLID):
    - ApiController: solo HTTP + JSON
    - MvcController: solo vistas HTML
    - Service: lógica de negocio (compartida)
    - Repository: acceso a datos
    - Mapper: conversión de datos
4. Documentación
    - Swagger

### Tecnologías

- Spring Boot
    - Data-JPA
    - Data-Rest
    - WebMVC
        - Thymeleaf
    - Validation
    - Librerías
        - lombok
        - devtools
- MySQL
- Maven

### Utilidades
- Configurado sistema de archivo de logs, tanto del proyecto como un apartado solo con los mensajes de ERROR para una revisión más rápida
***

## Instalación y puesta en marcha

Ejecutar aplicación en entornos
mvn spring-boot:run '-Dspring-boot.run.arguments=--spring.profiles.active=dev'

Para limpiar la caché y cargar el proyecto
mvn clean compile spring-boot:run '-Dspring-boot.run.arguments=--spring.profiles.active=dev'
