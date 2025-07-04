package com.example.webflux_study.handler;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

@Component
public class EchoWebSocketHandler implements WebSocketHandler {

    @Override
    @NonNull
    public Mono<Void> handle(@NonNull WebSocketSession session) {
        return session.send(
                session.receive()
                        .map(msg -> session.textMessage(msg.getPayloadAsText())));
    }
}
