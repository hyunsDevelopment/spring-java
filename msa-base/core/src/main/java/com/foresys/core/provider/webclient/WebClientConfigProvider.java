package com.foresys.core.provider.webclient;

import io.micrometer.observation.ObservationRegistry;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebClientConfigProvider {

    public WebClient webClient(ObservationRegistry observationRegistry) {
        return WebClient.builder()
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE
                        , MediaType.APPLICATION_JSON_VALUE
                )
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                                        .responseTimeout(Duration.ofMillis(30000))
                                        .doOnConnected(conn -> {
                                            conn.addHandlerLast(
                                                    new ReadTimeoutHandler(
                                                            30000
                                                            ,TimeUnit.MILLISECONDS))
                                                    .addHandlerLast(
                                                            new WriteTimeoutHandler(
                                                                    30000
                                                                    ,TimeUnit.MILLISECONDS));
                                        })
                        )
                )
                .observationRegistry(observationRegistry)
                .build();
    }

}
