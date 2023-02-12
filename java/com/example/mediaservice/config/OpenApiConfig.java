package com.example.mediaservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// в свагер добавляем авторизацию
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

public class OpenApiConfig {
    @Bean
    public OpenAPI mediaServiceProject(){
        return new OpenAPI()
                .info(new Info()
                        .description("Сайт кинотеки")
                        .title("Кинотека")
                        .version("v0.1")
                        .contact(new Contact().name("Gulnaz F."))
                );
    }
}
