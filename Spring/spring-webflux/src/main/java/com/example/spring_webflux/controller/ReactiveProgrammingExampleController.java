package com.example.spring_webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reactive")
public class ReactiveProgrammingExampleController {

    @GetMapping("/onenine/legacy")
    public Mono<List<Integer>> produceOneToNineLegacy() {
        return Mono.fromCallable(() -> {
            List<Integer> sink = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                sink.add(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return sink;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/onenine/list")
    public List<Integer> produceOneToNine() {
        List<Integer> sink = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            sink.add(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // WebFlux에서 단순 객체로 return하게 되면 자동으로 Mono.just() 를 wrap하여 반환한다.
        return sink;
    }

    @GetMapping("/onenine/list-defer")
    public Mono<List<Integer>> produceOneToNineDefer() {
        return Mono.defer(() -> {
            List<Integer> sink = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                sink.add(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return Mono.just(sink);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/onenine/flux")
    public Flux<Integer> produceOneToNineFlux() {
        return Flux.<Integer>create(sink -> {
            for (int i = 0; i < 9; i++) {
                sink.next(i);
                try {
                    log.info("현재 처리되는 스레드 : " + Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            sink.complete();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
