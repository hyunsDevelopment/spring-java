package com.example.webflux_study.handler;

import java.net.URI;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.example.webflux_study.manager.SessionManager;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PingWebSocketHandler implements WebSocketHandler {

    private final SessionManager sessionManager;

    @Override
    @NonNull
    public Mono<Void> handle(@NonNull WebSocketSession session) {
        String userId = extractUserId(session);
        sessionManager.add(userId, session);
        return session.receive()
                .doFinally(signalType -> sessionManager.remove(userId))
                .then();
    }

    private String extractUserId(WebSocketSession session) {
        URI uri = session.getHandshakeInfo().getUri();
        String query = uri.getQuery();

        if (query == null || !query.contains("userId="))
            return null;

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2 && pair[0].equals("userId")) {
                return pair[1];
            }
        }
        return null;
    }
}
