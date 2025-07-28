package com.kiwoom.app.system.config.security;

import com.kiwoom.app.system.config.jwt.JwtProvider;
import com.kiwoom.app.system.config.jwt.properties.JwtProperties;
import com.kiwoom.app.system.config.profile.ProfileComponent;
import com.kiwoom.app.system.dto.ComRes;
import com.kiwoom.app.system.exception.BusinessExceptionCode;
import com.kiwoom.app.system.filter.JwtAuthenticationFilter;
import com.kiwoom.app.system.util.ConvertUtil;
import com.kiwoom.app.system.util.HttpSupportUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ProfileComponent profileComponent;

    private final JwtProperties jwtProperties;

    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;

    private static final String[] swaggerPaths = {"/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/sample/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtProperties.getExcludePaths(), jwtProvider, userDetailsService);

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            profileComponent.isProd() ?
                                    jwtProperties.getExcludePaths() :
                                    Stream.of(jwtProperties.getExcludePaths(), swaggerPaths)
                                            .flatMap(Arrays::stream)
                                            .toArray(String[]::new)
                    ).permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, ex1) -> {
                        if (HttpSupportUtil.isIncludedPath(swaggerPaths, req.getRequestURI())) {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND);
                            return;
                        }
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        res.getWriter().write(this.setResponseMessage(BusinessExceptionCode.UNAUTHORIZED));
                    })
                    .accessDeniedHandler((req, res, ex2) -> {
                        if (HttpSupportUtil.isIncludedPath(swaggerPaths, req.getRequestURI())) {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND);
                            return;
                        }
                        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
                        res.getWriter().write(this.setResponseMessage(BusinessExceptionCode.FORBIDDEN));
                    })
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private String setResponseMessage(BusinessExceptionCode code) {
        String message = "처리중 오류가 발생하였습니다.";
        try {
            message = ConvertUtil.ObjToJsonstring(
                    ComRes.builder()
                            .rsltCd(code.getCode())
                            .rsltMsg(code.getMessage())
                            .build()
            );
        } catch (Exception ignored) {}
        return message;
    }
}
