package com.webflux.test.reactor.operator.changeSequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FlatMapOperator2 {
    // FlatMap이용 구구단
    public static void main(String[] args) throws InterruptedException {
        Flux
                .range(2, 8)
                .flatMap(num -> Flux
                        .range(1, 9 )
                        // flatmap Inner Sequence 비동기 실행
                        // 순서를 보장 받지 못한다
                        .publishOn(Schedulers.parallel())
                        .map(num2 -> num * num2))
                .subscribe(result -> log.info(result.toString()));
        Thread.sleep(200L);
    }
}
