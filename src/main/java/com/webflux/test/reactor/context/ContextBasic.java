package com.webflux.test.reactor.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class ContextBasic {
    public static void main(String[] args) throws InterruptedException {
        Mono
                .deferContextual(ctx ->
                        Mono
                                .just("hello" + " " + ctx.get("firstName"))
                                .doOnNext(data -> log.info("# just doOnNext : {}", data)))
                                .subscribeOn(Schedulers.boundedElastic())
                                .publishOn(Schedulers.parallel())
                                .transformDeferredContextual(
                                        (mono, ctx2) -> mono.map(data -> data + " " + ctx2.get("lastName"))
                                )
                                .contextWrite(context -> context.put("lastName", "Jobs"))
                                .contextWrite(context -> context.put("firstName", "Steve"))
                                .subscribe(data -> log.info("# onNext: {}", data));

                Thread.sleep(100L);
    }
}