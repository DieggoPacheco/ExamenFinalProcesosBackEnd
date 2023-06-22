package com.procesos.concesionario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Examen Final De Procesos")
                        .version("3.0.0")
                        .description("Api rest de un crud para gestionar usuarios y también productos de un proyecto, efecto del examen final de procesos del negocio")
                        .termsOfService("http://procesos.com")
                        .license(new License().name("Examen final").url("http://examen.com"))
                        .contact(new Contact()
                                .name("Diego Pacheco and Jesus Clavijo")
                                .email("dapachecopa@ufpso.edu.co")
                                .url("http://hola.com")));
    }
}
