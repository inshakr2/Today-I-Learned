package com.chany.jwt.config;

import com.chany.jwt.filter.JwtAuthenticationFilter;
import com.chany.jwt.filter.MyFilter1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        // http.addFilter(new MyFilter1()); 커스텀 필터는 Security 어떤 필터 뒤에 혹은 다음에 추가할 건지 명시해주어야 함
//        http.addFilterBefore(new MyFilter1(), SecurityContextPersistenceFilter.class);
        // 참고로 Servlet 필터보다는 Security 필터가 먼저 작동함.
        // 따라서 만약 모든 필터들 중에 가장 먼저 적용하고자 한다면 Security 필터 중 가장 먼저 걸려 있는 필터 before에 걸어주면 된다.
        // 그 필터가 SecurityContextPersistenceFilter


        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) // @CrossOrigin은 인증이 없을 때 사용하고, 인증이 있을 경우 SecurityFilter에 등록해야함.
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .formLogin().disable()
                .httpBasic().disable() // Basic Auth (ID + PW Header에 담아 요청하는 방식) 을 사용하지 않는다.
                .authorizeRequests()
                    .antMatchers("/api/v1/user/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")
                    .antMatchers("/api/v1/manager/**")
                        .hasAnyRole("MANAGER", "ADMIN")
                    .antMatchers("/api/v1/admin/**")
                        .hasRole("ADMIN")
                    .anyRequest().permitAll();

    }
}
