import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Publisher {

    public Flux<Integer> startFlux() {
//        Flux.just(1, 2, 3, 4, 5);
//        Flux.fromIterable(List.of("a", "b", "c"));
        return Flux.range(1, 10).log();
    }


    public Flux<String> startFlux2() {
        return Flux.fromIterable(List.of("a", "b", "c"));
    }

    public Mono<Integer> startMono() {
        return Mono.just(999).log();
    }

    public Mono<?> startMono2() {
        return Mono.empty().log();
    }

    public Mono<?> startMono3() {
        return Mono.error(new RuntimeException("reactor error"));
    }
}
