package com.webflux.test.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class DropStrategy {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureDrop(dropped -> log.info("# dropped: {}", dropped))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                                log.info("# onNext: {}", data);
                            } catch (InterruptedException e) {}
                        },
                        error -> log.error("# onError", error));

        Thread.sleep(2000L);
    }
}
