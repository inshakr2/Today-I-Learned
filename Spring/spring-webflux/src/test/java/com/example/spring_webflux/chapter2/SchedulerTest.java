package com.example.spring_webflux.chapter2;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class SchedulerTest {

    /*
    스케줄러 할당 가능한 구간 = subscribe / publish 될 때
     */

    @Test
    public void testBasicFluxMono() {
        Mono.<Integer>just(2)
                .map(data -> {
                    System.out.println("map Thread = " + Thread.currentThread().getName());
                    return data * 2;
                })
                .publishOn(Schedulers.parallel())
                .filter(data -> {
                    System.out.println("filter Thread = " + Thread.currentThread().getName());
                    return data % 4 == 0;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(data -> System.out.println("Mono가 구독한 data = " + data));
    }

}
