package com.kiwoom.app.system.config.swagger;

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
    GroupedOpenApi groupAllApi() {
        return GroupedOpenApi.builder()
                .group("00. All")
                .packagesToScan("com.kiwoom.app")
                .build();
    }

    @Bean
    GroupedOpenApi groupAuthApi() {
        return GroupedOpenApi.builder()
                .group("01. Auth")
                .packagesToScan("com.kiwoom.app.auth")
                .build();
    }

    @Bean
    GroupedOpenApi groupSampleApi() {
        return GroupedOpenApi.builder()
                .group("99. Sample")
                .packagesToScan("com.kiwoom.app.sample")
                .build();
    }
}
