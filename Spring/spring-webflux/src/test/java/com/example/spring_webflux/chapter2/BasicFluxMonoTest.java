package com.example.spring_webflux.chapter2;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BasicFluxMonoTest {

    @Test
    public void testBasicFluxMono() {
        // 빈 함수 혹은 데이터로 부터 시작할 수 있다.
        // Flux = 0개 이상의 데이터를 방출 (List / Stream)
        Flux.<Integer>just(1, 2, 3, 4, 5)
                .map(data -> data * 2)
                .filter(data -> data % 4 == 0)
                .subscribe(data -> System.out.println("Flux가 구독한 data = " + data));

        // Mono = 0개, 1개의 데이터를 방출 (Optional)
        Mono.<Integer>just(2)
                .map(data -> data * 2)
                .filter(data -> data % 4 == 0)
                .subscribe(data -> System.out.println("Mono가 구독한 data = " + data));
    }

    @Test
    public void testFluxMonoBlock() {
        Mono<String> justString = Mono.just("String");
        // Mono와 같은 객체는 언제 객체 생성이 될 지 모르는 비동기 객체임.
        // 강제로 String 객체로 바꾸는 가장 쉬운 방법은 block을 사용하는 것
        // 만약 Mono 안에서의 스레드가 작업 시간이 오래걸리는 작업을 한다고 한다면,
        // 해당 비동기 객체가 데이터 생성이 완료되는 시점까지 대기(Block)하게 됨 (실제 방출이 일어나는 시점)
        String string = justString.block();

    }
}