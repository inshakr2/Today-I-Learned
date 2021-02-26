package chany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        RestTemplate restTemplate = restTemplateBuilder.build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // hello
        String helloResult = restTemplate.getForObject("http://localhost:9090/hello", String.class);
        System.out.println("helloResult = " + helloResult);

        // world
        String worldResult = restTemplate.getForObject("http://localhost:9090/world", String.class);
        System.out.println("worldResult = " + worldResult);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
