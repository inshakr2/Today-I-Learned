package io.security.oauth2.springsecurityoauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                req -> req.anyRequest().authenticated()
            )
            .formLogin(withDefaults())
//            .apply(new CustomSecurityConfigurer().setFlag(false));
            .with(new CustomSecurityConfigurer(), customSecurityConfigurer -> customSecurityConfigurer.setFlag(false));

        return http.build();
    }
}
