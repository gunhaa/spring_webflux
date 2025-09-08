package com.webflux.test.reactor.basic;

import reactor.core.publisher.Flux;

public class ReactorExample4 {
    public static void main(String[] args) {
        Flux.concat(
                        Flux.just("Mercury", "Venus", "Earth"),
                        Flux.just("Mars", "Jupiter", "Saturn"),
                        Flux.just("Uranus", "Neptune", "Pluto")
                ) // flux가 반환된다
                .collectList()
                // 반환된 flux를 Mono로 리턴한다(단일 컬렉션)
                .subscribe(planets -> System.out.println(planets));
                // 단일 컬렉션을 출력한다
    }
}
