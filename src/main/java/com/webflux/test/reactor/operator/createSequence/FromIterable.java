package com.webflux.test.reactor.operator.createSequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class FromIterable {
    public static void main(String[] args) throws InterruptedException {
        Flux
                .fromIterable(List.of(new Coin("BTC", 50000000), new Coin("ETH", 1700000)))
                // streams를 진정한 비동기로 만들기 위해 필요한 Operation
                // .subscribeOn(Schedulers.boundedElastic())
                .subscribe(coin ->
                        log.info("coin명: {}, 현재가: {}", coin.getCoinName(), coin.getCurPrice())
                        );
        Thread.sleep(100L); // 지원팀이 일할 시간을 벌어주기 위해 잠시 대기
    }

    @Data
    @AllArgsConstructor
    static class Coin {
        String coinName;
        int curPrice;
    }
}
