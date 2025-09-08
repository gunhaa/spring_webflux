package com.webflux.test.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BufferDropLatestStrategy {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(300L))
                .doOnNext(data -> log.info("# emitted by original Flux: {}", data))
                .onBackpressureBuffer(2,
                        dropped -> log.info("** Overflow & Dropped: {} **", dropped),
                        BufferOverflowStrategy.DROP_LATEST)
                .doOnNext(data -> log.info("[ # emiteed by Buffer: {} ]", data))
                                               // 전달받아 data 처리 가능,
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(data -> {
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                            }
                            log.info("# onNext: {}", data);
                        },
                        error -> log.error("# onError", error));
        Thread.sleep(3000L);
    }
}
