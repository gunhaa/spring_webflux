package com.webflux.test.reactor.debug;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Hooks {
    static Map<String, String> fruits = new HashMap<>();

    static {
        fruits.put("banana", "바나나");
        fruits.put("apple", "사과");
        fruits.put("pear", "배");
        fruits.put("graph", "포도");
    }

    @SneakyThrows
    public static void main(String[] args) {
        reactor.core.publisher.Hooks.onOperatorDebug();
        Flux
                .fromArray(new String[]{"BANANAS" , "APPLES", "PEARS", "MELONS"})
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .map(String::toLowerCase)
                .map(fruit -> fruit.substring(0, fruit.length() - 1))
                .map(fruit -> fruits.get(fruit))
                .map(translated -> "맛있는" + translated)
                .subscribe(
                        log::info,
                        error -> log.error("# onError: ", error)
                );
        Thread.sleep(100L);
    }
}
