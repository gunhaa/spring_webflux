package com.webflux.test.reactor.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class ErrorStrategy {
    public static void main(String[] args) throws InterruptedException {
        Flux
                // 0부터 증가한 숫자 전송(0.001초에 한번)
                .interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                // 별도의 스레드 추가 생성/ Schedulers
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                // 전달 받은 데이터 처리 속도 0.005초로 설정
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {
                            }
                            log.info("#onNext: {}", data);
                        },
                        error -> log.error("# onError: " , error));
        // reactor.core.Exceptions$OverflowException: The receiver is overrun by more signals than expected (bounded queue...)
        // 해당 코드는 소비자가 소비하는 속도보다 버퍼에 쌓이는 속도가 빨라 StackOverflow Exception이 발생한다
        // 버퍼는 기본 256개, 255에서 문제가 발생한다
        // 버퍼에 있는 데이터가 빠져나가지 않아 문제가 발생한다
        Thread.sleep(2000L);
    }
}