package in.java.oes2026.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authProvider
    ) throws Exception {

        http
                // ================= CORS =================
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                // ================= STATELESS =================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ================= AUTH PROVIDER =================
                .authenticationProvider(authProvider)

                // ================= SECURITY RULES =================
                .authorizeHttpRequests(auth -> auth

                        // ===== PUBLIC =====
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/api/upcoming-exam/**")
                        .permitAll()
                        // ===== ADMIN =====
                        .requestMatchers("/api/admin/**")
                        .hasAuthority("ROLE_ADMIN")

                        // ===== STUDENT =====
                        .requestMatchers("/api/student/**")
                        .hasAuthority("ROLE_STUDENT")

                        // ===== EXAMS (BOTH) =====
                        .requestMatchers("/api/exams/**")
                        .hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN")

                        .requestMatchers("/api/student/exam/submit")
                        .hasAnyAuthority("ROLE_STUDENT")

                        // ===== QUESTIONS (BOTH) =====
                        .requestMatchers("/api/questions/**")
                        .hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN")

                        // ===== RESULTS (BOTH) =====
                        .requestMatchers("/api/results/**")
                        .hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN")

                        // ===== DEFAULT =====
                        .anyRequest().authenticated()
                )

                // ================= JWT FILTER =================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // ================= CORS CONFIG =================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // FIX: use allowedOrigins instead of patterns (faster + stable)
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://online-exam-2026.netlify.app"
        ));

        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        config.setAllowedHeaders(List.of("*"));

        config.setAllowCredentials(true);

        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // ================= AUTH PROVIDER =================
    @Bean
    public AuthenticationProvider authenticationProvider(
            org.springframework.security.core.userdetails.UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {

        org.springframework.security.authentication.dao.DaoAuthenticationProvider provider =
                new org.springframework.security.authentication.dao.DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    // ================= AUTH MANAGER =================
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}