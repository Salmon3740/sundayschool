package com.tmcf.sundayschool.config;

import com.tmcf.sundayschool.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // disable CSRF (stateless JWT)
                .csrf(csrf -> csrf.disable())

                // stateless session (no server-side session)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints — auth API and pages
                        .requestMatchers("/", "/api/auth/**").permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // Thymeleaf pages — secured by JS JWT, permit at Spring level
                        .requestMatchers("/dashboard", "/students", "/students/**",
                                "/attendance", "/lessons", "/tests",
                                "/songs-stories", "/reports", "/class/**")
                        .permitAll()

                        // Teacher-only write operations
                        .requestMatchers(HttpMethod.POST, "/api/attendance/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/students/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/tests/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/lessons/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/classes/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST, "/api/songs-stories/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("TEACHER")

                        // All authenticated users can read
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()

                        // Everything else needs authentication
                        .anyRequest().authenticated())

                // disable form login and basic auth
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())

                // add JWT filter before Spring's default auth filter
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
