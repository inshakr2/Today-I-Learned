package com.chany.jwt.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chany.jwt.auth.PrincipalDetails;
import com.chany.jwt.model.User;
import com.chany.jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

// Security filter 중 BasicAuthenticationFilter
// 권한 / 인증이 필요한 특정 주소를 요청했을 때 위 filter 적용됨
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }


    // 인증이나 권한이 필요한 요청이 올 경우 아래 필터를 타게됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("JwtAuthorizationFilter.doFilterInternal");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader = " + jwtHeader);

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        String token = jwtHeader.replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512("secret"))
                                    .build()
                                    .verify(token)
                                    .getClaim("username")
                                    .asString();
        if (username != null) {

            User userEntity = userRepository.findByUsername(username);

            // JWT 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다.
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
            Collection<? extends GrantedAuthority> authorities = principalDetails.getAuthorities();
            System.out.println("authorities = " + authorities);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, authorities);

            // Security Session 에 강제로 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);

    }
}
