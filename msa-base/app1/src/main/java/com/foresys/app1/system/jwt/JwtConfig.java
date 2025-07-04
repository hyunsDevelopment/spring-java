package com.foresys.app1.system.jwt;

import com.foresys.core.component.jwt.JwtComponent;
import com.foresys.core.properties.jwt.JwtProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JwtConfig {

    @Bean("jwtProperties")
    @ConfigurationProperties("jwt")
    @Primary
    JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    @Primary
    JwtComponent jwtComponent(@Qualifier("jwtProperties") JwtProperties jwtProperties) {
        return new JwtComponent(jwtProperties);
    }

}
