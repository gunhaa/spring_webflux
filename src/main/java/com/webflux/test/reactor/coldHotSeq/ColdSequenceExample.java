package com.webflux.test.reactor.coldHotSeq;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class ColdSequenceExample {
    public static void main(String[] args) throws InterruptedException {
        // 구독이 발생할 때마다 처음부터 실행 - cold Sequence 동작이다
        Flux<String> coldFlux =
                Flux
                        .fromIterable(Arrays.asList("KOREA", "JAPAN", "CHINESE"))
                        .map(String::toLowerCase);
        coldFlux.subscribe(country -> log.info("# Subscriber1: {}", country));
        System.out.println("-------------------------------------");
        Thread.sleep(2000L);
        coldFlux.subscribe(country -> log.info("# Subscriber2: {}", country));
    }
}
