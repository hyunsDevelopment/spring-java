package com.sample.sequence.service;

import com.sample.sequence.util.DateUtil;
import com.sample.sequence.util.RedisKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SequenceService {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    public Mono<String> getRedisSequence(String name) {
        String dateString = DateUtil.getCurrentDate("yyyyMMdd");
        String key = RedisKeyUtil.getSequenceKey(name, dateString);

        return reactiveStringRedisTemplate
                .opsForValue()
                .increment(key)
                .flatMap(seq -> reactiveStringRedisTemplate
                        .expire(key, DateUtil.getRemainingUntilMidnight())
                        .thenReturn(dateString + String.format("%09d", seq)));
    }

    public Mono<String> getRedisCurrentSequence(String name) {
        String dateString = DateUtil.getCurrentDate("yyyyMMdd");
        String key = RedisKeyUtil.getSequenceKey(name, dateString);

        return reactiveStringRedisTemplate
                .opsForValue()
                .get(key)
                .map(val -> {
                    long seq = Long.parseLong(val);
                    return dateString + String.format("%09d", seq);
                })
                .switchIfEmpty(Mono.just("NO SEQUENCE!"));
    }

}
