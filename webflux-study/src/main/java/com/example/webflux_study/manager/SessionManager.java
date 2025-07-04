package com.example.webflux_study.manager;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

@Component
public class SessionManager {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void add(String sessionId, WebSocketSession session) {
        sessions.put(sessionId, session);
    }

    public void remove(String sessionId) {
        sessions.remove(sessionId);
    }

    public Optional<WebSocketSession> get(String userId) {
        return Optional.ofNullable(sessions.get(userId));
    }

    public Collection<WebSocketSession> getAll() {
        return sessions.values();
    }
}
