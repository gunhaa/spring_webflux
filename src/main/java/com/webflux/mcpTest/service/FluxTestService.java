package com.webflux.mcpTest.service;

import com.webflux.mcpTest.mockRepository.MockRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
public class FluxTestService {

    @SneakyThrows
    public static Mono<List<Integer>> reactiveTest1() {
        return Mono.just(1)
                .doOnNext(data -> log.info("first do on next data: {}", data))
//                .publishOn(Schedulers.boundedElastic())
                .subscribeOn(Schedulers.boundedElastic())
                .map(i -> i + 2)
                .doOnNext(data -> log.info("second do on next data: {}", data))
                .flatMap(data -> {
                    try {
                        Thread.sleep(data);
                        log.info("나는 {}초 동안 쉬었다.. Thread Name: {}", data, Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return Mono.just(List.of(data+1));
                });
    }

    @SneakyThrows
    public static Mono<List<Integer>> reactiveTest2() {
        return Flux.range(1,10)
                .doOnNext(data -> log.info("first do on next data: {}", data))
                .publishOn(Schedulers.boundedElastic())
                .map(i -> i + 2)
                .doOnNext(data -> log.info("second do on next data: {}", data))
                .flatMap(data -> {
                    try {
                        Thread.sleep(data);
                        log.info("나는 {}초 동안 쉬었다.. Thread Name: {}", data, Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return Mono.just(data);
                })
                .collectList();
    }

    public static Mono<List<Integer>> mockDbTest1() {
        MockRepository mockRepository = new MockRepository();
        return Flux.fromIterable(List.of(1,2,3,4,5))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(data -> mockRepository.save(data))
                .publishOn(Schedulers.parallel())
                .map(saveResult -> saveResult + 10)
                .collectList()
                .doOnSuccess(list -> log.info("최종 리스트: {} ", list));
    }

    public static Mono<List<Integer>> mockDbTest2() {
        MockRepository mockRepository = new MockRepository();
        return Flux.fromIterable(List.of(1,2,3,4,5))
                .flatMap(data -> {
                    return mockRepository.save(data)
                            .subscribeOn(Schedulers.boundedElastic());
                })
                .collectList()
                .doOnSuccess(list -> log.info("최종 리스트: {} ", list));
    }

    @SneakyThrows
    public static void main(String[] args) {
//        reactiveTest1().subscribe();
//        reactiveTest2().subscribe();
        mockDbTest2().subscribe();

        Thread.sleep(10000);
    }

}
