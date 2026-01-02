package com.example.spring_webflux.chapter2;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class BasicMonoOperatorTest {

    // just
    // empty 사소한 에러가 발생했을 때 로그를 남기고 empty의 Mono를 전파할 때 사용
    @Test
    public void startMonoFromData() {
        Mono.just(1).subscribe(System.out::println);

        Mono.empty().subscribe(System.out::println);
    }

    // fromCallable 동기적인 객체를 반환 (Flux, Mono 등 아무 객체나 반환 가능함)
    // defer Mono 반환할 때 사용
    @Test
    public void startMonoFromFunction() {
        // 이 방법은 임시 마이그레이션에서 사용하면 좋다.
        // restTemplate, JPA 같은 블로킹이 발생하는 라이브러리 Mono로 스레드 분리하여 처리 가능
        Mono<String> monoFromCallable = Mono.fromCallable(() -> {
            // 로직 실행 후 동기 객체를 반환
            return "fromCallable!";
            // return callRestTemplate(); 이런 식으로 사용할 수 있다.
        }).subscribeOn(Schedulers.boundedElastic());

        // Mono.just 와의 차이라면, defer는 구독하는 시점에 데이터가 생성된다.
        Mono<String> monoFromDefer = Mono.defer(() -> {
            return Mono.just("defer!");
        });
        monoFromDefer.subscribe();
    }

    @Test
    public void testDeferNecessity() {
        String a = "안녕";
        String b = "하세";
        String c = "요";
        Mono<String> stringMono = callWebClient(a + b + c);
        // 위에서 callWebClient 자체는 Mono로 잘 동작하지만, a/b/c 를 만드는 로직 자체도 Mono로 관리하고자 한다면 defer를 사용해야 한다.

        // 즉, 모든 로직을 Mono로 묶고 구독활 때 실행시키고 싶은 경우 사용할 수 있다.
        // 이렇게 하면 a/b/c 와 같은 데이터를 만드는 로직에서 블로킹이 발생해도 안전하게 처리할 수 있게 된다.
        // @See produceOneToNineDefer
        Mono<String> stringMonoFromDefer = Mono.defer(() -> {
            String a2 = "안녕";
            String b2 = "하세"; // blocking!
            String c2 = "요";
            return callWebClient(a2 + b2 + c2);
        }).subscribeOn(Schedulers.boundedElastic());

    }

    public Mono<String> callWebClient(String request) {
        return Mono.just(request + "callWebClient");
    }

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
    public void monoToFlux() {
        Mono<Integer> one = Mono.just(1);
        Flux<Integer> integerFlux = one.flatMapMany(data -> {
            return Flux.just(data, data + 1, data + 2);
        });
        integerFlux.subscribe(System.out::println);
    }
}
