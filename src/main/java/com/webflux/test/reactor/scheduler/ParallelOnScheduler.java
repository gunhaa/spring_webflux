package com.webflux.test.reactor.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ParallelOnScheduler {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{1,3,5,7,9,11,13,15,17,19})
                .parallel()
                // parellel() 방식은 동시성을 가지는 논리적인 스레드를 사용하는 subscribeOn()/publishOn()과 다르게
                // 병렬성을 가지는 물리적인 스레드이다
                // parellel()의 경우 라운드 로빈 방식으로 CPU코어 개수만큼의 스레드를 병렬로 실행한다
                // (물리적인 코어 갯수가 아닌 논리적인 코어의 개수, intel은 하이퍼스레딩으로 인해 1개의 코어에서 2개의 논리적인 스레드로 사용된다)
                // 예를 들어 4코어 8스레드의 CPU라면 총 8개의 스레드를 병렬로 실행한다
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));
        Thread.sleep(100L);
    }
}
