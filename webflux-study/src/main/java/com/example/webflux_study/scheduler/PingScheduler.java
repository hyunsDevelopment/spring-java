package com.example.webflux_study.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.webflux_study.manager.SessionManager;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class PingScheduler {

    private final SessionManager sessionManager;

    @Scheduled(fixedDelay = 1000)
    public void ping() {
        for (var session : sessionManager.getAll()) {
            if (session.isOpen())
                session.send(Mono.just(session.textMessage("ping"))).subscribe();
        }
    }
}
