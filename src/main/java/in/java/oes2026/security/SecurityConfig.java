package in.java.oes2026.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authProvider)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC
                        // =========================
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        // =========================
                        // STUDENT ACCESS
                        // =========================
                        .requestMatchers("/api/student/**").hasRole("STUDENT")
                        .requestMatchers("/api/student/exam/submit").hasRole("STUDENT")

                        // =========================
                        // EXAMS (READ ONLY for students, write for examiner/admin)
                        // =========================
                        .requestMatchers(HttpMethod.GET, "/api/exams/**")
                        .hasAnyRole("STUDENT", "EXAMINER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/exams/**")
                        .hasAnyRole("EXAMINER", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/exams/**")
                        .hasAnyRole("EXAMINER", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/exams/**")
                        .hasAnyRole("EXAMINER", "ADMIN")

                        // =========================
                        // QUESTIONS
                        // =========================
                        .requestMatchers(HttpMethod.GET, "/api/questions/**")
                        .hasAnyRole("STUDENT", "EXAMINER", "ADMIN")

                        .requestMatchers("/api/questions/**")
                        .hasAnyRole("EXAMINER", "ADMIN")

                        // =========================
                        // RESULTS
                        // =========================
                        .requestMatchers("/api/results/**")
                        .hasAnyRole("STUDENT", "ADMIN", "EXAMINER")

                        // =========================
                        // ADMIN ONLY
                        // =========================
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // =========================
                        // OTHERS SECURE
                        // =========================
                        .anyRequest().authenticated()
                )

                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "https://online-exam-2026.netlify.app"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setExposedHeaders(List.of("Authorization"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}