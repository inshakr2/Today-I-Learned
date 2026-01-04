package com.example.spring_webflux.chapter2;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class OperatorFlatMapTest {
    /*
    FlatMap : Mono를 떼준다.
     Mono<Mono<T>> -> Mono<T>
     Flux<Mono<T>> -> Flux<T> => 이 구조 안에 있는 Mono는 flatMap / merge 를 통해 벗겨낼 수 있다.
                                 순서를 보장해야할 경우 sequential 사용
     Mono<Flux<T>> -> Flux<T> => flatMapMany를 사용한다. 순서 보장
     Flux<Flux<T>> -> collectList --> Flux<Mono<List<T>>> --> Flux<List<T>>
      Block을 사용하는 것은 권장하지 않음.
     */

    @Test
    public void monoToFlux() {
        Mono<Integer> one = Mono.just(1);
        Mono<Flux<Integer>> map = one.map(data -> {
            return Flux.just(data, data + 1, data + 2);
        });
        // 단순하게 map을 사용하면 비동기 객체 안에 비동기 객체가 들어간 기형적인 구조를 갖게된다.
        // 어차피 비동기 + 비동기 객체라면 하나의 비동기 객체로 보아도 무방하다 (무한대 + 무한대 = 무한대 처럼)
        // 이렇게 중첩된 비동기 객체를 1개로 평탄화 하는 것이 FlatMap
        // 특정 비동기 객체의 흐름 안에서ㅏ WebClient같은 비동기 작업을 호출하거나 비동기 객체를 반환하는 함수를 호출하면
        // 이런 중첩된 형태의 비동기 객체가 생성되게 되며 생각보다 빈번하게 발생한다.
    }

    @Test
    public void testWebClientFlatMap() {
        // 중첩된 객체
        Flux<Mono<String>> nested1 = Flux.just(
                callWebClient("1단계 - 문제 이해하기", 1500),
                callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
                callWebClient("3단계 - 최종 응답", 500));

        Flux<Mono<String>> nested2 = Flux.<Mono<String>>create(sink -> {
            sink.next(callWebClient("1단계 - 문제 이해하기", 1500));
            sink.next(callWebClient("2단계 - 문제 단계별로 풀어가기", 1000));
            sink.next(callWebClient("3단계 - 최종 응답", 500));
            sink.complete();
        });

        Flux<String> flatMap = Flux.just(
                        callWebClient("1단계 - 문제 이해하기", 1500),
                        callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
                        callWebClient("3단계 - 최종 응답", 500))
                .flatMap(mono -> {
                    return mono;
                });
        flatMap.subscribe(data -> System.out.println("flatten data = " + data));
        /*
         FlatMap은 기본적으로 데이터 입력 순서에 관계없이 처리 순서에 따라서 데이터를 방출한다. 즉, 처리 순서가 빠른 순으로 방출한다.
        flatten data = 3단계 - 최종 응답 -> 딜레이 : 500
        flatten data = 2단계 - 문제 단계별로 풀어가기 -> 딜레이 : 1000
        flatten data = 1단계 - 문제 이해하기 -> 딜레이 : 1500
         */

        Flux<String> flatMapSequential = Flux.just(
                        callWebClient("1단계 - 문제 이해하기", 1500),
                        callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
                        callWebClient("3단계 - 최종 응답", 500))
                .flatMapSequential(mono -> {
                    return mono.map(data -> data + "추가 가공!");
                });
        flatMapSequential.subscribe(data -> System.out.println("flatten Sequential data = " + data));
        /*
         순서를 보장하려면 flatMapSequential 사용
        flatten Sequential data = 1단계 - 문제 이해하기 -> 딜레이 : 1500
        flatten Sequential data = 2단계 - 문제 단계별로 풀어가기 -> 딜레이 : 1000
        flatten Sequential data = 3단계 - 최종 응답 -> 딜레이 : 500
         */

        // 추가 데이터 가공없이 Mono만 떼어내는 작업을 한다면 merge를 사용하는 것이 더 깔끔하다.
        Flux<String> merge = Flux.merge(
                        callWebClient("1단계 - 문제 이해하기", 1500),
                        callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
                        callWebClient("3단계 - 최종 응답", 500));
//                .map(data -> data + "추가 가공!"); 여기에 map을 쓰면 flatMap과 유사하게 사용가능함
        merge.subscribe(data -> System.out.println("merge data = " + data));
        /*
        merge data = 3단계 - 최종 응답 -> 딜레이 : 500
        merge data = 2단계 - 문제 단계별로 풀어가기 -> 딜레이 : 1000
        merge data = 1단계 - 문제 이해하기 -> 딜레이 : 1500
         */

        Flux<String> mergeSequential = Flux.mergeSequential(
                callWebClient("1단계 - 문제 이해하기", 1500),
                callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
                callWebClient("3단계 - 최종 응답", 500));
        mergeSequential.subscribe(data -> System.out.println("merge data = " + data));

        // concat = 동기적으로 처리된다. 즉 1단계 호출/처리 완료 후 2단계 호출/처리. 2단계 호출/처리 완료 후 3단계...
        // mergeSequential / flatMapSequential 과는 다름. 이 둘은 일괄로 실행시키고 마지막에 다시 순서대로 정리하는 것임.
        // 따라서 concat / concatMap 은 사용하지 말자.
        Flux<String> concat = Flux.concat(
                callWebClient("1단계 - 문제 이해하기", 1500),
                callWebClient("2단계 - 문제 단계별로 풀어가기", 1000),
                callWebClient("3단계 - 최종 응답", 500));


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<String> callWebClient(String request, long delay) {
        return Mono.defer(() -> {
                    try {
                        Thread.sleep(delay);
                        return Mono.just(request + " -> 딜레이 : " + delay);
                    } catch (Exception e) {
                        return Mono.empty();
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Test
    public void MonoFluxTest() {
        Mono<Flux<String>> nested = Mono.<Flux<String>>fromCallable(() -> {
            return callFunction("Hello");
        });
        nested.subscribe(data -> System.out.println("data = " + data));

        Flux<String> flux = nested.flatMapMany(data -> {
            return data;
        });

        flux.subscribe(data -> System.out.println("data = " + data));

    }

    public Flux<String> callFunction(String data) {
        return Flux.<String>create(sink -> {
            sink.next(data + "1");
            sink.next(data + "2");
            sink.next(data + "3");
            sink.complete();
        });
    }
}
