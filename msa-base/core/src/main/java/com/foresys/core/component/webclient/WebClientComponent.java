package com.foresys.core.component.webclient;

import com.foresys.core.properties.jwt.JwtProperties;
import com.foresys.core.util.ConnectUtil;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
public class WebClientComponent {

    private WebClient webClient;

    private ObservationRegistry observationRegistry;

    private JwtProperties jwtProperties;

    public <T, K> K apiPost(String baseUrl, String uri, T obj, Class<K> cls) throws Exception {
        String clientIP = ConnectUtil.getClientIP();

        return Observation.createNotStarted("foresys", observationRegistry)
                .contextualName("httpGetConnection")
                .lowCardinalityKeyValue(uri, uri)
                .observe(() -> {

                    if(obj != null) {
                        return webClient.mutate()
                                .baseUrl(baseUrl)
                                .defaultHeader("X-Forwarded-For", clientIP)
                                .defaultHeader(jwtProperties.getAccess().getNamePrefix(), "")
                                .defaultHeader(jwtProperties.getRefresh().getNamePrefix(), "")
                                .build()
                                .post()
                                .uri(uri)
                                .body(Mono.just(obj), obj.getClass())
                                .retrieve()
                                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.just(new RuntimeException("WebClient 통신 중 오류가 발생하였습니다. (HttpStatusCode: 4xx)")))
                                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.just(new RuntimeException("WebClient 통신 중 오류가 발생하였습니다. (HttpStatusCode: 5xx)")))
                                .bodyToMono(cls)
                                .block();
                    }else {
                        return webClient.mutate()
                                .baseUrl(baseUrl)
                                .defaultHeader("X-Forwarded-For", clientIP)
                                .defaultHeader(jwtProperties.getAccess().getNamePrefix(), "")
                                .defaultHeader(jwtProperties.getRefresh().getNamePrefix(), "")
                                .build()
                                .post()
                                .uri(uri)
                                .retrieve()
                                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.just(new RuntimeException("WebClient 통신 중 오류가 발생하였습니다. (HttpStatusCode: 4xx)")))
                                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.just(new RuntimeException("WebClient 통신 중 오류가 발생하였습니다. (HttpStatusCode: 5xx)")))
                                .bodyToMono(cls)
                                .block();
                    }
                });
    }

}
