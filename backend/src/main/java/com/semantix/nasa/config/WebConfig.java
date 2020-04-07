package com.semantix.nasa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author frfontoura
 * @version 1.0 07/04/2020
 */
@Configuration
public class WebConfig {

    @Value("${security.allowed-origins}")
    private String allowedOrigins;

    /**
     * Enables Cross-Origin Resource Sharing (CORS)
     *
     * @return a WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins);
            }
        };
    }

}
