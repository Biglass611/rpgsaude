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

    // 1. Endpoints ABERTOS (Públicos)
    // ADICIONEI AS ROTAS DO SITE AQUI EMBAIXO
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/users/login",
            "/users/criar",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/",                 // Raiz
            "/index.html",       // Arquivo principal do site
            "/rpgsaude/**",      // Pasta específica que você mostrou no print
            "/assets/**",        // CSS e JS
            "/static/**",
            "/favicon.ico"
    };

    // 2. Endpoints USER
    public static final String [] ENDPOINTS_USER = {
            "/users/atualizar/{id}",
            "/users/listarPorId/{id}",
            "/api/avatar/listar",
            "/api/avatar/listarPorId/{id}",
            "/api/avatar/criar",
            "/api/avatar/atualizar/{id}",
            "/api/avatar/deletar/{id}",
            "/api/avatar/{id}/adicionar-moedas",
            "/api/avatar/{id}/atualizar-atributos",
            "/api/dungeon/listar",
            "/api/dungeon/listarPorId/{id}",
            "/api/dungeon/criar",
            "/api/dungeon/atualizar/{id}",
            "/api/dungeon/deletar/{id}",
            "/api/dungeon/ranking/{desafioId}",
            "/api/desafio/listar",
            "/api/desafio/listarPorId/{id}",
            "/api/recompensa/listar",
            "/api/recompensa/listarPorId/{id}"
    };

    // 3. Endpoints ADMIN
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/listar",
            "/users/deletar/{id}",
            "/api/desafio/criar",
            "/api/desafio/deletar/{id}",
            "/api/recompensa/criar",
            "/api/recompensa/atualizar/{id}",
            "/api/recompensa/deletar/{id}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Libera rotas públicas (Login, Swagger e SITE agora)
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. Rotas de ADMIN
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")

                        // 3. Rotas de USER
                        .requestMatchers(ENDPOINTS_USER).authenticated()

                        // 4. Qualquer outra rota também exige autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}