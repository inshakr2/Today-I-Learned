package com.example.spring_webflux.chapter2;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicFluxOperatorTest {

    /**
     * Flux
     *  데이터 : just empty from~
     *  함수 : defer create
     */
    @Test
    public void testFluxFromData() {
        Flux.just(1, 2, 3, 4)
                .subscribe(data -> System.out.println("data = " + data));

        List<Integer> basicList = List.of(1, 2, 3, 4);
        Flux.fromIterable(basicList)
                .subscribe(data -> System.out.println("data = " + data));
    }

    // Flux defer -> Flux 반환
    // Flux create -> 동기적인 객체 혹은 Mono, Flux 반환
    @Test
    public void testFluxFromFunction() {
        Flux.defer(() -> {
            return Flux.just(1,2,3,4);
        }).subscribe(data -> System.out.println("data from defer = " + data));

        Flux.<Integer>create(sink -> {
            // next 를 사용해 값을 추가할 수 있다.
            sink.next(1);
            sink.next(2);
            sink.next(3);
            sink.complete(); // complete 를 마지막에 꼭 사용해야함
        }).subscribe(data -> System.out.println("data from sink = " + data));

        Flux.create(sink -> {
            sink.next(1);
            sink.next("스트링");
            sink.next(9L);
            sink.complete();
        }).subscribe(data -> System.out.println("data from sink = " + data));
    }

    // 동기적인 로직 마이그레이션
    // 복잡한 로직 안에서 Flux에 방출 타이밍을 제어하고 싶을 때
    @Test
    public void testSinkDetail() {
        Flux.<String>create(sink -> {
            AtomicInteger counter = new AtomicInteger(0);
            recursiveFunction(sink, counter);
        }).subscribe(data -> System.out.println("data = " + data));
    }

    public void recursiveFunction(FluxSink<String> sink, AtomicInteger counter) {
        if (counter.incrementAndGet() < 10) {
            sink.next("sink count " + counter);
            recursiveFunction(sink, counter);
        } else {
            sink.complete();
        }
    }

    @Test
    public void testSinkDetailContext() {
        Flux.<String>create(sink -> {
            AtomicInteger counter = new AtomicInteger(0);
            recursiveFunction(sink);
        })
                .contextWrite(Context.of("counter", new AtomicInteger(0)))
                .subscribe(data -> System.out.println("data = " + data));
    }

    public void recursiveFunction(FluxSink<String> sink) {
        AtomicInteger counter = sink.contextView().get("counter");
        if (counter.incrementAndGet() < 10) {
            sink.next("sink count " + counter);
            recursiveFunction(sink);
        } else {
            sink.complete();
        }
    }

    @Test
    public void testFluxCollectList() {
        Mono<List<Integer>> listMono = Flux.<Integer>just(1, 2, 3, 4, 5)
                .map(data -> data * 2)
                .filter(data -> data % 4 == 0)
                .collectList();

        listMono.subscribe(data -> System.out.println("collectList가 반환한 List = " + data));
    }
}