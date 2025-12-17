package com.webflux.mcpTest.mockRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class MockRepository {

    private final ConcurrentHashMap<String, Integer> repository = new ConcurrentHashMap<>();

    @SneakyThrows
    public Mono<Integer> save(Integer i) {
        return Mono.fromCallable(() -> {
                    String threadName = Thread.currentThread().getName();
                    repository.put(threadName, i);
                    return i;
                })
                .flatMap(saveData -> {
                    long delay = ThreadLocalRandom.current().nextLong(1000, 5001);
                    return Mono.just(saveData)
                            .delayElement(Duration.ofMillis(delay));
                })
                .doOnSuccess(data -> log.info("Save Success.. Data: {}", data));
    }

    public ConcurrentHashMap<String, Integer> getRepository() {
        return this.repository;
    }

    public void selectAll() {
        log.info(">> Mock Repository : {}" , this.repository);
    }

    public void clear() {
        this.repository.clear();
        log.info(">> Mock Repository Clear");
    }

}
