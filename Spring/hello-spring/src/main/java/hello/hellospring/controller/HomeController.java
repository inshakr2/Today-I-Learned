package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 컨트롤러가 정적 파일보다 우선순위가 높다.
    // static index.html이 있어도 Controller에 "/"이 맵핑 되어있으면 우선권을 가진다.

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
