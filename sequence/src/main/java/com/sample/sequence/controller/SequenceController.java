package com.sample.sequence.controller;

import com.sample.sequence.service.SequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sequence")
@RequiredArgsConstructor
public class SequenceController {

    private final SequenceService sequenceService;

    @GetMapping("/redis/{name}")
    public Mono<String> getRedisSequence(@PathVariable String name) {
        return sequenceService.getRedisSequence(name);
    }

    @GetMapping("/redis/{name}/current")
    public Mono<String> getRedisCurrentSequence(@PathVariable String name) {
        return sequenceService.getRedisCurrentSequence(name);
    }

}
