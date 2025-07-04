package com.kiwoom.app.system.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kiwoom API")
                        .version("1.0"));
    }

    @Bean
    GroupedOpenApi groupUsrApi() {
        return GroupedOpenApi.builder()
                .group("99. Sample")
                .packagesToScan("com.kiwoom.app.sample")
                .build();
    }
}
