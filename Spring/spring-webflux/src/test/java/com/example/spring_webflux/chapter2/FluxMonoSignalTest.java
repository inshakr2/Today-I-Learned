package com.example.spring_webflux.chapter2;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMonoSignalTest {

    @Test
    public void testBasicSignal() {
        Flux.just(1, 2, 3, 4)
                .doOnNext(publishedData -> System.out.println("publishedData = " + publishedData))
                .map(data -> data * 2)
                .doOnComplete(() -> System.out.println("스트림 종료"))
                .doOnError(ex -> {
                    System.out.println("에러 발생 : " + ex);
                })
                .subscribe(data -> System.out.println("data = " + data));
        /*
        publishedData = 1
        data = 2
        publishedData = 2
        data = 4
        publishedData = 3
        data = 6
        publishedData = 4
        data = 8
        스트림 종료
         */
    }

    @Test
    public void testBasicSignal2() {
        Flux.just(1, 2, 3, 4)
                .map(data -> data * 2)
                .doOnNext(publishedData -> System.out.println("publishedData = " + publishedData))
                .doOnComplete(() -> System.out.println("스트림 종료"))
                .doOnError(ex -> {
                    System.out.println("에러 발생 : " + ex);
                })
                .subscribe(data -> System.out.println("data = " + data));
        /*
        publishedData = 2
        data = 2
        publishedData = 4
        data = 4
        publishedData = 6
        data = 6
        publishedData = 8
        data = 8
        스트림 종료
         */
    }

    @Test
    public void testFluxMonoError() {
        try {
            Flux.just(1, 2, 3, 4)
                    .map(data -> {
                        if (data == 3) {
                            throw new RuntimeException();
                        }
                        return data * 2;
                    })
//                   모든 오페러이터에서 실제론 아래와 같이 동작한다.
//                    .map(data -> {
//                        try {
//                            if (data == 3) {
//                                throw new RuntimeException();
//                            }
//                            return data * 2;
//                        } catch (Exception e) {
//                            // 에러 시그널 방출
//                        }
//                    })
                    .subscribe(data -> System.out.println("data = " + data));
        } catch (Exception e) {
            System.out.println("에러!");
        }
        /*
         위 로직에서 catch로 RuntimeException을 잡을 수가 없다. 왜 그럴까?
         java > 예외를 찾을 때 코드를 실행중인 스레드의 호출 스택을 거슬러 올라가 찾을 수 있다.
          ** 호출 스택 = 스레드에서 관리되는 메모리. 스레드의 함수가 호출될 때 마다 함수에 대한 정보를 스택 형태로 저장함. 각 스택은 스레드에서 독립적으로 관리
         webFlux에서는 실제로 Flux에서 실행되는 비동기 객체들의 여러 로직들은 실제로 main 스레드가 아닌 별도로 할당된 스레드에 의해 실행되고,
         그 안에서 예외가 발생하더라도 메인 스레드는 그 예외를 감지할 수가 없다.
         위와 같이 별도 스레드를 할당하지 않아도 리액티브 스트림에서는 스레드가 수없이 많이 바뀌기 때문에 애초에 설계할 때 에러를 블록하지 않는 이상 밖으로 던지지 않고
         스트림 안에서 해결하도록 설계된 것이다.
         이처럼 데이터가 방출되는 중간에 에러가 발생한 경우, 즉각적으로 방출을 중단하고 에러 시그널을 보내게 된다.
         컨트롤러에서 데이터 방출 중 이러한 에러가 발생한다면 기본적으로 500에러를 반환하며, ControllerAdvice / ExceptionHandler를 통해 커스텀할 수 있다.
         */
    }

    // operator 내 try-catch 블록 : 에러가 발생하여도 스트림을 계속 이어나가고 싶은 경우에 사용한다.
    @Test
    public void testFluxMonoErrorCatch() {
        try {
            Flux.just(1, 2, 3, 4)
                    .map(data -> {
                        try {
                            if (data == 3) {
                                throw new RuntimeException();
                            }
                            return data * 2;
                        } catch (Exception e) {
                            // data.setError();
                            return data * 999;
                        }
                    })
                    .subscribe(data -> System.out.println("data = " + data));
        } catch (Exception e) {
            System.out.println("에러!");
        }
    }

    // 전파되는 에러 시그널을 사용하고 싶은 경우
    @Test
    public void testFluxMonoErrorOperator() {
        try {
            Flux.just(1, 2, 3, 4)
                    .map(data -> {
                        if (data == 3) {
                            throw new RuntimeException();
                        }
                        return data * 2;
                    })
                    .onErrorMap(ex -> new IllegalArgumentException()) // 커스터마이징 에러 던질때
                    .onErrorReturn(-1) // 에러가 났을 경우 방출하고 싶은 데이터
                    .onErrorComplete() // 에러가 발생해도 에러 시그널을 전파하지 않고 바로 컴플릿 시그널 전파
                    .subscribe(data -> System.out.println("data = " + data));
        } catch (Exception e) {
            System.out.println("에러!");
        }
    }

    // Flux,Mono.error()
    @Test
    public void testFluxMonoDotError() {
        Flux.just(1, 2, 3, 4)
                .flatMap(data -> {
                    if (data != 3) {
                        return Mono.just(data);
                    } else {
                        // 직접적으로 에러 시그널 발생
                        return Mono.error(new RuntimeException());
                        // 리액티브 스트림 내부 코드에서 에러 시그널로 바꿔서 처리 --> 사실상 동일하게 동작
//                        throw new RuntimeException();
                    }
                }).subscribe(data -> System.out.println("data = " + data));
    }
}
