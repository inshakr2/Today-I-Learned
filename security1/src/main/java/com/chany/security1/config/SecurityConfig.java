package com.chany.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 필터 체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// securedEnabled : Secured 어노테이션 활성화 & prePostEnabled : preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm") // 로그인 페이지 강제 지정 (로그인이 필요한 페이지일 경우 지정한 endpoint 로 redirection
                .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행. 즉 Controller에 /login을 만들지 않아도 됨
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm");



    }
}
