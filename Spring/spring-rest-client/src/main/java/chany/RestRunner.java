package chany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    WebClient.Builder builder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebClient webClient = builder.build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // hello
        Mono<String> helloResult = webClient.get().uri("http://localhost:9090/hello")
                .retrieve()
                .bodyToMono(String.class);
        helloResult.subscribe(s -> {
            System.out.println(s);

            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });

        // world
        Mono<String> worldResult = webClient.get().uri("http://localhost:9090/world")
                .retrieve()
                .bodyToMono(String.class);

        worldResult.subscribe(s -> {
            System.out.println(s);

            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });
    }
}
