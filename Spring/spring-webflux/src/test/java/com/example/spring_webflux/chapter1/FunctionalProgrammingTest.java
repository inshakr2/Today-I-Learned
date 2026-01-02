package com.example.spring_webflux.chapter1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

@SpringBootTest
public class FunctionalProgrammingTest {

    @Test
    public void produceOneToNine() {
        List<Integer> sink = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            sink.add(i);
        }

        // 모든 요소 *2
        sink = map(sink, (data) -> data * 4);

        // 4의 배수들만 필터링
        sink = filter(sink, (data) -> data % 4 == 0);

        forEach(sink, (data) -> System.out.println(data));
    }

    @Test
    public void produceOneToNineStream() {
        // 1
        List<Integer> sink = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            sink.add(i);
        }

        sink.stream()
                .map(s -> s * 4)
                .filter(s -> s % 4 == 0)
                .forEach(s -> System.out.println(s)); // 최종 연산 필수 - forEach / min / max / collect

        // 2
        IntStream.rangeClosed(1, 9).boxed()
                .map(s -> s * 4)
                .filter(s -> s % 4 == 0)
                .forEach(s -> System.out.println(s));
    }

    @Test
    public void produceOneToNineFlux() {
        Flux<Integer> intFlux = Flux.create(sink -> {
                for (int i = 1; i <= 9; i++) {
                    sink.next(i);
                }
                sink.complete();
            });

        intFlux.subscribe(data -> System.out.println("WebFlux가 구독 중! : " + data));
        System.out.println("Netty 이벤트 루프로 스레드 복귀!");
    }

    @Test
    public void produceOneToNineOperator() {
        Flux.fromIterable(IntStream.rangeClosed(1, 9).boxed().toList())
                .map(s -> s * 4)
                .filter(s -> s % 4 == 0)
                .subscribe(s -> System.out.println(s));

    }

    private static void forEach(List<Integer> sink, Consumer<Integer> consumer) {
        for (int i = 0; i < sink.size(); i++) {
            consumer.accept(sink.get(i));
        }
    }

    private static List<Integer> filter(List<Integer> sink, Function<Integer, Boolean> predicate) {
        List<Integer> sink3 = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            if (predicate.apply(sink.get(i))) {
                sink3.add(sink.get(i));
            }
        }
        sink = sink3;
        return sink;
    }

    private static List<Integer> map(List<Integer> sink, Function<Integer, Integer> mapper) {
        List<Integer> sink2 = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            sink2.add(mapper.apply(sink.get(i)));
        }
        sink = sink2;
        return sink;
    }
}
