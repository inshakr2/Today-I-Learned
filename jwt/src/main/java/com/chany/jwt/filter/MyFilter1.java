package com.chany.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("###############################");
        System.out.println("FILTER1");
        System.out.println("###############################");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 이런 방식으로 토큰을 만들어 주어야 한다.
        // 1. ID / PW 로그인 인증이 성공
        // 2. 토큰 발급
        // 3. 요청할 때마다 해당 토큰이 넘어오면 해당 토큰을 검증 (유효성 / 무결성)
        if (req.getMethod().equalsIgnoreCase("post")) {
            String headerAuth = req.getHeader("Authorization");
            System.out.println("headerAuth = " + headerAuth);

            if (headerAuth.equalsIgnoreCase("chany")) {
                chain.doFilter(request, response);
            } else {
                PrintWriter writer = res.getWriter();
                writer.println("인증실패");
            }
        }


    }
}
