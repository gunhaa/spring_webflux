package com.webflux.test.reactor.coldHotSeq;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class HotSequenceExample {
    public static void main(String[] args) throws InterruptedException{
        // Hot Sequence는 구독한 시점의 타임라인부터 emit된 데이터를 받을 수 있다
        String[] singers = {"Singer A", "Singer B", "Singer C", "Singer D", "Singer E"};

        log.info("# Begin concert: ");

        Flux<String> concertFlux =
                Flux
                        .fromArray(singers)
                        // 데이터 소스로 입력된 각 데이터의 emit을 일정시간 동안 지연시키는 Operator
                        // Operator의 디폴트 스레드 스케쥴러가 Parallel이기에, parallel-1,2,3.. 등의 스레드가 로그에 찍힌다
                        .delayElements(Duration.ofSeconds(1))
                        // Hot Sequence로 동작하게 해주는 Operator
                        .share();

        concertFlux.subscribe(
                singer -> log.info("# Subscriber1 is watching {}'s song", singer)
        );

        Thread.sleep(2500);

        concertFlux.subscribe(
                singer -> log.info("# Subscriber2 is watching {}'s song", singer)
        );

        Thread.sleep(3000);
    }
}