package com.foresys.core.aop.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foresys.core.model.res.ComRes;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ComResAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String packageName = returnType.getDeclaringClass().getPackageName();
        return !packageName.startsWith("org.springdoc") && !packageName.startsWith("org.springframework.boot.actuate");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
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
