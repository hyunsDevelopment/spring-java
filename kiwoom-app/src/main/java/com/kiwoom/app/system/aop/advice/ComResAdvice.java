package com.kiwoom.app.system.aop.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwoom.app.system.dto.ComRes;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ComResAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        String packageName = returnType.getDeclaringClass().getPackageName();
        return !packageName.startsWith("org.springdoc") && !packageName.startsWith("org.springframework.boot.actuate");
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if(body instanceof ComRes<?>) {
            return body;
        }
        if (body instanceof String b) {
            try {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return new ObjectMapper().writeValueAsString(new ComRes<>(b));
            } catch (Exception e) {
                throw new RuntimeException("Error wrapping String response", e);
            }
        }
        return new ComRes<>(body);
    }
}
