package com.devo.lease.adapter.web.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI leaseOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Lease API")
                .description("MVP monolith (Clean Architecture + DDD). Endpoints to lease and return cars.")
                .version("v1")
                .contact(new Contact().name("Team Lease")));
    }

}
