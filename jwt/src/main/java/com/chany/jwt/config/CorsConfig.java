package com.chany.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        // 서버가 응답할 때 Json을 JavaScript에서 처리할 수 있게 할지를 설정
        config.setAllowCredentials(true);

        // 모든 IP 응답 허용
        config.addAllowedOrigin("*");

        // 모든 Header에 응답 허용
        config.addAllowedHeader("*");

        // 모든 HTTP Method 요청 허용
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
