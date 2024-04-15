import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class Operator2 {

    public Flux<Integer> fluxConcatMap() {
        return Flux.range(1, 10)
                .concatMap(i -> Flux.range(i * 10, 10)
                        .delayElements(Duration.ofMillis(100))
                        .map(j -> {
                            System.out.printf("%d * %d = %d\n", i, j, i*j);
                            return j;
                        }))
                .log();
    }
    public Flux<Integer> fluxFlatMapMany() {
        return Mono.just(10)
                .flatMapMany(i -> Flux.range(1, i))
                .log();
    }

    public Mono<Integer> defaultIfEmpty() {
        return Mono.just(100)
                .filter(i -> i > 100)
                .defaultIfEmpty(-1)
                .log();
    }

    public Mono<Integer> switchIfEmpty1() {
        return Mono.just(100)
                .filter(i -> i > 100)
                .switchIfEmpty(
                        Mono.just(50)
                                .map(i -> i / 10))
                .log();
    }

    public Mono<Integer> switchIfEmpty2() {
        return Mono.just(100)
                .filter(i -> i > 100)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ERROR!")))
                .log();
    }

    public Flux<String> fluxMerge() {
        return Flux.merge(Flux.fromIterable(List.of("a", "b", "c")),
                Flux.just("99")).log();
    }

    public Flux<String> monoMerge() {
        return Mono.just("A")
                .mergeWith(Mono.just("B"))
                .mergeWith(Mono.just("C"))
                .log();
    }

    public Flux<String> fluxZip() {
        return Flux.zip(
                        Flux.just("a", "b", "c"),
                        Flux.just("1", "2", "3")
                ).map(i -> i.getT1() + ":" + i.getT2())
                .log();
    }

    public Mono<Integer> monoZip() {
        return Mono.zip(
                        Mono.just(1),
                        Mono.just(2),
                        Mono.just(3),
                        Mono.just(4)
                ).map(i -> i.getT1() + i.getT2() + i.getT3() + i.getT4())
                .log();
    }
}
