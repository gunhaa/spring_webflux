package com.webflux.test.reactor.operator.createSequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class JustOrEmpty {
    public static void main(String[] args) {
        // just()의 확장 Operator로서
        // just()의 Operator와 달리 emit할 데이터가 null인 경우 NPE가 발생하지 않고
        // onComplete Signal을 전송한다
        Mono
                // Optional.ofNullable(null)도 같은 결과는 같다
                .justOrEmpty(null)
                .subscribe(data -> {},
                        error -> {},
                        () -> log.info("#onComplete"));
    }
}
