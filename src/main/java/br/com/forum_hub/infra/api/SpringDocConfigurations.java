package br.com.forum_hub.infra.api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(
                                "bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                        ))
                .info(new Info()
                        .title("Social Login")
                        .description("Java e Spring Security: Login Social")
                        .contact(new Contact()
                                .name("Rafael")
                                .email("rafael_junio_moraes@hotmail.com")
                                .url("https://rafaelmoraesdev.com.br"))
                        .license(new License()
                                .name("M.I.T")
                        )
                );
    }
}
