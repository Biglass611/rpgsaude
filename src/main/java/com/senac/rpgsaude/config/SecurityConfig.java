package com.senac.rpgsaude.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    // --- 1. Endpoints ABERTOS (Públicos) ---
    // Inclui versões com e sem "/rpgsaude" para compatibilidade total
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            // -- Padrão (Localhost) --
            "/users/login",
            "/users/criar",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/",
            "/index.html",
            "/assets/**",
            "/static/**",
            "/favicon.ico",
            "/*.apk",
            "/*.png",
            "/*.jpg",
            "/css/**",
            "/js/**",
            "/error", // Importante liberar erro

            // -- Servidor Faculdade (com prefixo /rpgsaude) --
            "/rpgsaude/users/login",
            "/rpgsaude/users/criar",
            "/rpgsaude/v3/api-docs/**",
            "/rpgsaude/swagger-ui/**",
            "/rpgsaude/swagger-ui.html",
            "/rpgsaude/",
            "/rpgsaude/index.html",
            "/rpgsaude/assets/**",
            "/rpgsaude/static/**",
            "/rpgsaude/favicon.ico",
            "/rpgsaude/*.apk",
            "/rpgsaude/*.png",
            "/rpgsaude/*.jpg",
            "/rpgsaude/css/**",
            "/rpgsaude/js/**",
            "/rpgsaude/error"
    };

    // --- 2. Endpoints USER ---
    public static final String [] ENDPOINTS_USER = {
            "/users/atualizar/{id}",
            "/users/listarPorId/{id}",
            "/api/avatar/**",
            "/api/dungeon/**",
            "/api/desafio/**",
            "/api/recompensa/listar",
            "/api/recompensa/listarPorId/{id}",
            // Prefixo
            "/rpgsaude/users/atualizar/{id}",
            "/rpgsaude/users/listarPorId/{id}",
            "/rpgsaude/api/avatar/**",
            "/rpgsaude/api/dungeon/**",
            "/rpgsaude/api/desafio/**",
            "/rpgsaude/api/recompensa/listar",
            "/rpgsaude/api/recompensa/listarPorId/{id}"
    };

    // --- 3. Endpoints ADMIN ---
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/listar",
            "/users/deletar/{id}",
            "/api/recompensa/criar",
            "/api/recompensa/atualizar/{id}",
            "/api/recompensa/deletar/{id}",
            // Prefixo
            "/rpgsaude/users/listar",
            "/rpgsaude/users/deletar/{id}",
            "/rpgsaude/api/recompensa/criar",
            "/rpgsaude/api/recompensa/atualizar/{id}",
            "/rpgsaude/api/recompensa/deletar/{id}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. FORÇA A LIBERAÇÃO DO LOGIN (Normal e Prefixo)
                        .requestMatchers(HttpMethod.POST, "/users/login", "/rpgsaude/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/criar", "/rpgsaude/users/criar").permitAll()

                        // 2. Libera o resto dos arquivos públicos
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()

                        // Libera OPTIONS geral
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 3. Rotas de ADMIN
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")

                        // 4. Rotas de USER
                        .requestMatchers(ENDPOINTS_USER).authenticated()

                        // 5. O resto exige login
                        .anyRequest().authenticated()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}