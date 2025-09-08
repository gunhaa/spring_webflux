package com.webflux.test.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class PublishOnScheduler {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                // publishOn() Operator는 해당 publishOn()을 기준으로 Downstream의 실행 스레드를 변경한다
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("#onNext: {}", data));
        Thread.sleep(500L);
    }
}
