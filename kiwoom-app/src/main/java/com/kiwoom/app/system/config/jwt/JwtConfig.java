package com.kiwoom.app.system.config.jwt;

import com.kiwoom.app.system.config.jwt.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    JwtProvider jwtHandler(@Qualifier("jwtProperties") JwtProperties jwtProperties) {
        return new JwtProvider(jwtProperties);
    }
}
