package com.webflux.mcpTest.flatMap;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class Test {
    public static void main(String[] args) {
        Flux.fromIterable(List.of(1,2,3))
                .flatMap(i -> {
                    // BAD, main thread block
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("i+3: {}, job 종료", i+1);
                    return Mono.just(i + 1);
                })
                .subscribe();
    }
}
