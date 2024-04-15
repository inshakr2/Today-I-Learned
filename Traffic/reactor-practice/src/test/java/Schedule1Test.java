import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class Schedule1Test {

    private Schedule1 schedule1 = new Schedule1();

    @Test
    void fluxMapWithSubscribeOn() {
        StepVerifier.create(schedule1.fluxMapWithSubscribeOn())
                .expectNextCount(10)
                .verifyComplete();
    }


    @Test
    void fluxMapWithPublishOn() {
        StepVerifier.create(schedule1.fluxMapWithPublishOn())
                .expectNextCount(10)
                .verifyComplete();
    }
}