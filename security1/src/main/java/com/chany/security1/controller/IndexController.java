package com.chany.security1.controller;

import com.chany.security1.model.User;
import com.chany.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println("user = " + user);
        user.setRole("ROLE_USER");

        // password 암호화가 되지 않으면 시큐리티로 로그인을 할 수 없음.
        String rawPwd = user.getPassword();
        String encPwd = passwordEncoder.encode(rawPwd);
        user.setPassword(encPwd);

        userRepository.save(user);

        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN") //특정 메서드에 접근 제어 @EnableGlobalMethodSecurity(securedEnabled = true) 활성화 필요
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 메서드 실행되기 직전에 실행됨
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터 정보";
    }
}

