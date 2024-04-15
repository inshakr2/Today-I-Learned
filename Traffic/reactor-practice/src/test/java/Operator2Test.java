import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class Operator2Test {

    private Operator2 operator2 = new Operator2();

    @Test
    void fluxConcatMap() {
        StepVerifier.create(operator2.fluxConcatMap())
                .expectNextCount(100)
                .verifyComplete();
    }

    @Test
    void fluxFlatMapMany() {
        StepVerifier.create(operator2.fluxFlatMapMany())
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .verifyComplete();

    }

    @Test
    void defaultIfEmpty() {
        StepVerifier.create(operator2.defaultIfEmpty())
                .expectNext(-1)
                .verifyComplete();
    }

    @Test
    void switchIfEmpty1() {
        StepVerifier.create(operator2.switchIfEmpty1())
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    void switchIfEmpty2() {
        StepVerifier.create(operator2.switchIfEmpty2())
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void fluxMerge() {
        StepVerifier.create(operator2.fluxMerge())
                .expectNext("a", "b", "c", "99")
                .verifyComplete();
    }

    @Test
    void monoMerge() {
        StepVerifier.create(operator2.monoMerge())
                .expectNext("A", "B", "C")
                .verifyComplete();
    }

    @Test
    void fluxZip() {
        StepVerifier.create(operator2.fluxZip())
                .expectNext("a:1", "b:2", "c:3")
                .verifyComplete();
    }

    @Test
    void monoZip() {
        StepVerifier.create(operator2.monoZip())
                .expectNext(10)
                .verifyComplete();
    }
}