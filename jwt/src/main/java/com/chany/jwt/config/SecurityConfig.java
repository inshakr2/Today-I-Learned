package com.chany.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) // @CrossOrigin은 인증이 없을 때 사용하고, 인증이 있을 경우 SecurityFilter에 등록해야함.
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
