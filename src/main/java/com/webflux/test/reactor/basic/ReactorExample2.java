package com.webflux.test.reactor.basic;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

public class ReactorExample2 {
    public static void main(String[] args) {
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Non-Blocking의 이점은 얻을 수 없지만 요청과 응답을 하나의 Operator 체인으로 깔끔하게 처리할 수 있는 장점이 있다
        Mono.just(
            restTemplate
                    .exchange(worldTimeUri,
                            HttpMethod.GET,
                            new HttpEntity<String>(headers),
                            String.class)
        )
                .map(response -> {
                    DocumentContext jsonContext =
                            JsonPath.parse(response.getBody());
//                    System.out.println(jsonContext.);
                    String datetime = jsonContext.read("$.datetime");
                    return datetime;
                })
                .subscribe(
                        data -> System.out.println("# emitted data: " + data),
                        error -> {
                            System.out.println();
                        },
                        () -> System.out.println("# emitted onComplete signal")
                );
    }
}
