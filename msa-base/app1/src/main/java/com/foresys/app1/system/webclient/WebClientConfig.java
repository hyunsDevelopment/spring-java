package com.foresys.app1.system.webclient;

import com.foresys.core.component.webclient.WebClientComponent;
import com.foresys.core.properties.jwt.JwtProperties;
import com.foresys.core.provider.webclient.WebClientConfigProvider;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("observedAspect")
    @Primary
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    @Bean("observationRegistry")
    @Primary
    ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }

    @Bean("webClient")
    @Primary
    WebClient webClient(@Qualifier("observationRegistry") ObservationRegistry observationRegistry) {
        return new WebClientConfigProvider().webClient(observationRegistry);
    }

    @Bean
    @Primary
    WebClientComponent webClientComponent(
            @Qualifier("webClient") WebClient webClient
            ,@Qualifier("observationRegistry") ObservationRegistry observationRegistry
            ,@Qualifier("jwtProperties") JwtProperties jwtProperties
    ) {
        return new WebClientComponent(
                webClient
                ,observationRegistry
                ,jwtProperties
        );
    }

}
