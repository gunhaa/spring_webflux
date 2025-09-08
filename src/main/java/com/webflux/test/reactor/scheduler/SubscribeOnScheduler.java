package com.webflux.test.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SubscribeOnScheduler {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5 ,7})
                // 원본 Publisher(Flux)의 동작을 처리하기 위한 스레드를 할당한다
                // Schedulers.boundedElastic()은 ExecutorService 기반의 스레드풀을 생성한 후
                // 그 안에서 정해진 수 만큼의 스레드를 사용하여 작업을 처리하고 작업이 종료된 스레드는 반납하여 재사용하는 방식이다
                // Blocking I/O작업을 효과적으로 처리하기 위한 방식이다
                .subscribeOn(Schedulers.boundedElastic())
                // doOnNext는 데이터 흐름에 영향을 주지 않고 몰래 엿보는 관찰자
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                // 구독이 발생한 시점에 추가적인 처리가 필요하다면 동작 추가 가능
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                // subscribe는 데이터 흐름을 실제로 발동시키고 최종적으로 소비하는 실행자
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }
}
