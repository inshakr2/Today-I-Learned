package com.example.spring_webflux.chapter1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootTest
public class WebClientTest {

    private WebClient webClient = WebClient.builder().build();

    @Test
    public void testWebClient() {
        Flux<Integer> intFlux = webClient.get()
                .uri("http://localhost:8085/reactive/onenine/flux")
                .retrieve()
                .bodyToFlux(Integer.class);

        intFlux.subscribe(data -> {
            System.out.println("처리되고 있는 스레드 : " + Thread.currentThread().getName());
            System.out.println("WebFlux가 구독 중! : " + data);
        });

        System.out.println("Netty 이벤트 루프로 스레드 복귀!");

        // 테스트 환경에서 메인 스레드 잠시 잡아두기 위한 용도 - 이 처리를 하지 않을 경우 위 스케줄러가 메인 스레드와 함께 죽어버림!
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }

    }
}
