package chany;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(5000l);
        return "Hello";
    }
    @GetMapping("/world")
    public String wordl() throws InterruptedException {
        Thread.sleep(3000l);
        return "World";
    }
}
