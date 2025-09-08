package com.webflux.test.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class LatestStrategy {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureLatest()
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {}
                            log.info("#onNext: {}", data);
                        },
                        error -> log.error("# onError", error));
        // 결과는 255까지 출력되고,
        // 이후 1146부터 다시 출력된다
        // 버퍼가 가득 찼다가 버퍼가 다시 비워지는 시간 동안 emit되는 데이터가 가장 최근에 emit된 데이터가 된 후,
        // 다음 데이터가 emit되면 다시 폐기되는 과정을 반복하기 때문이다
        Thread.sleep(2000);
    }
}
