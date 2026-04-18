package com.college.erp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Cross-Origin Resource Sharing — applies only to <strong>browsers</strong> (e.g. your React SPA
 * when it runs on a different origin than the API).
 * <p>
 * Native Android, iOS, desktop, CLI, and server-to-server clients do <strong>not</strong> use CORS.
 * They typically send no {@code Origin} header; Spring treats those requests as non-CORS and this
 * configuration does not block them. Your JWT + HTTPS setup is what secures those clients.
 * <p>
 * If you later wrap the web UI in a hybrid shell (Capacitor, Ionic, WebView with a custom origin),
 * add that origin via {@code app.cors.extra-origin-patterns} (see application.properties).
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origin-patterns:http://localhost:5173,http://127.0.0.1:5173}") String patterns,
            @Value("${app.cors.extra-origin-patterns:}") String extraPatterns) {
        CorsConfiguration configuration = new CorsConfiguration();
        for (String p : (patterns + "," + extraPatterns).split(",")) {
            String t = p.trim();
            if (!t.isEmpty()) {
                configuration.addAllowedOriginPattern(t);
            }
        }
        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        // JWT in headers/body — no cookie credentials. false avoids CORS issues when the SPA uses fetch() without credentials (dev: 5173 → 8080).
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
