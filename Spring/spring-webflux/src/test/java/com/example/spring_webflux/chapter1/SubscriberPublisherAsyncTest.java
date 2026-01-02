package com.example.spring_webflux.chapter1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SpringBootTest
public class SubscriberPublisherAsyncTest {

    @Test
    public void produceOneToNineFlux() {
        Flux<Integer> intFlux = Flux.<Integer>create(sink -> {
            for (int i = 1; i <= 9; i++) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                sink.next(i);
            }
            sink.complete();
        }).subscribeOn(Schedulers.boundedElastic());

        intFlux.subscribe(data -> {
            System.out.println("처리되고 있는 스레드 : " + Thread.currentThread().getName());
            System.out.println("WebFlux가 구독 중! : " + data);
        });
        // 테스트 환경에서 메인 스레드 잠시 잡아두기 위한 용도 - 이 처리를 하지 않을 경우 위 스케줄러가 메인 스레드와 함께 죽어버림!
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        System.out.println("Netty 이벤트 루프로 스레드 복귀!");
    }
}


