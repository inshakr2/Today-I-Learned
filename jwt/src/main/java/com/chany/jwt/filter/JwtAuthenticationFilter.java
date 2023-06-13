package com.chany.jwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chany.jwt.auth.PrincipalDetails;
import com.chany.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


// UsernamePasswordAuthenticationFilter 는 Security Filter
// 기본적으로 loginProcessingUrl 경로로 username + password 를 post로 요청하면 동작
// 하지만 formLogin disable 하면 동작하지 않음! 따라서 JWT를 사용할 경우에는 상속받은 이 필터를 다시 등록해주면 된다!
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 으로 post 요청시 로그인 시도를 위해 실행되는 함수!
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter.attemptAuthentication");

        // 로그인 요청을 받아서,, authenticationManager 로 로그인을 시도하면
        // UserDetailsService.loadUserByUsername() 이 실행된다.
        // 따라서, PrincipalDetails 를 세션에 담고 (권한 관리를 위함) JWT 토큰을 만들어 응답해주면 된다.

        try {

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // 이 때 loadUserByUsername() 실행
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            // Authentication 객체가 Session 영역에 저장된다.
            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // attemptAuthentication 에서 성공적으로 로그인 처리가 되었다면 successfulAuthentication 함수가 실행됨.
    // 여기서 JWT 토큰을 만들고 client 에게 토큰을 response 해주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        Date expire = new Date(System.currentTimeMillis() + ( 60000 * 10) );

        String jwt = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(expire)
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("secret"));

        response.addHeader("Authorization", "Bearer " + jwt);
        // JWT 가 유효한지에 대한 필터가 필요함
    }
}
