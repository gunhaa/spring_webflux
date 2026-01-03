package com.webflux.mcpTest.error;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class Test {
    public static void main(String[] args) {
        Flux.fromIterable(List.of(1,2,3))
                .map(data -> data + 1)
                // doOnComplete는 어디에 있든 스트림이 종료되면 실행된다
                .doOnComplete(() -> log.info("doOnComplete"))
//                .map(data -> {
//                    if (true) {
//                        throw new IllegalStateException("나는 잘못됬음");
//                    }
//                    return data;
//                })
                .doOnNext(data -> log.info("map doOnNext data: {} ", data))
//                .flatMap(data -> {
//                    if(true) {
//                        return Mono.error(new IllegalStateException("잘못된 에러 결과임"));
//                    }
//                    return Mono.just(data - 1);
//                })
                .flatMap(data -> {
                    // map과 flatMap의 차이는 모노로 래핑하는 비동기 job을 처리가능한 오퍼레이터이다
                    return Mono.just(data - 1);
                })
                .doOnNext(data -> log.info("flatMap doOnNext data: {} ", data))
                .filter(data -> data < 3)
                .doOnNext(data -> log.info("filter doOnNext data: {} ", data))
                .onErrorResume(e -> {
                    log.info("에러가 발생해서 중지했음: {}", e.getMessage());
                    return Mono.empty();
                })
                .subscribe();
    }
}
