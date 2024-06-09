package io.security.oauth2.springsecurityoauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                req -> req.anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint);
            });
//            .apply(new CustomSecurityConfigurer().setFlag(false));
//            .with(new CustomSecurityConfigurer(), customSecurityConfigurer -> customSecurityConfigurer.setFlag(false));

        return http.build();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(
//                        req -> req.anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());
//            .apply(new CustomSecurityConfigurer().setFlag(false));
//            .with(new CustomSecurityConfigurer(), customSecurityConfigurer -> customSecurityConfigurer.setFlag(false));
//
//        return http.build();
//    }
}
