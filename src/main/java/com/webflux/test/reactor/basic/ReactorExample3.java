package com.webflux.test.reactor.basic;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorExample3 {
    public static void main(String[] args) {
        Flux<String> flux =
                // 두개의 mono를 잇는 방법
                // just는 null을 허용하지 않지만
                // justOrEmpty는 null을 허용한다
                Mono.justOrEmpty("Steve")
                        .concatWith(Mono.justOrEmpty("Jobs"));
        flux.subscribe(System.out::println);

        String a = "animal: ";
        String b = "pig";
        System.out.println(a.concat(b));
    }
}
