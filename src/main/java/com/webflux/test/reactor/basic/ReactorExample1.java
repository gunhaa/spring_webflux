package com.webflux.test.reactor.basic;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactorExample1 {
    public static void main(String[] args) {
        // Flux: Publisher
        // just.map은 operator로써 전달받은 데이터를 가공하는 역할을 한다
        Flux<String> sequence = Flux.just("Hello,", "Reactor", "Bye");
        sequence.map(data -> data.toLowerCase())
                // Subscriber
                .subscribe(data -> System.out.println(data));

        // Mono는 0, 혹은 1개의 인자인 Publisher이다
        Mono<String> monoSeq = Mono.just("Hello,");
        monoSeq.map(data -> data.toLowerCase())
                // Subscriber
                .subscribe(data -> System.out.println(data));

        Mono.just("Hello Reactor")
                .subscribe(System.out::println);

        Mono.empty()
                .subscribe(
                        // Subscriber가 Publisher로부터 데이터를 전달받기 위해 사용된다
                        none -> System.out.println("# emmited onNext signal"),
                        error -> {},
                        // empty이기에 emit이 되지 않고 complete된다
                        () -> System.out.println("# emiited onComplete signal")
                );
    }
}
