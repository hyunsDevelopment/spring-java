package com.example.webflux_study.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.example.webflux_study.handler.EchoWebSocketHandler;
import com.example.webflux_study.handler.PingWebSocketHandler;

@Configuration
public class WebSocketConfig {

    @Bean
    public SimpleUrlHandlerMapping handlerMapping(EchoWebSocketHandler echoHandler, PingWebSocketHandler pingHandler) {
        Map<String, Object> map = Map.of("/ws/echo", echoHandler, "/ws/ping", pingHandler);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(-1);
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
