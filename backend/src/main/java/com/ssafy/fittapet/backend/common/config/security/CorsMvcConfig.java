package com.ssafy.fittapet.backend.common.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Value("${frontend.server.url}")
    private String url;

    // 컨트롤러 CORS 문제 해결
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(url);
    }
}
