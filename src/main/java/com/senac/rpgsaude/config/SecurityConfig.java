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

import java.util.List; // Usando List.of (mais moderno)
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    // --- 1. Endpoints ABERTOS (Públicos) ---
    // NOTA: Não precisa colocar /rpgsaude aqui, pois o Spring já remove o contexto.
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
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
            // IMPORTANTE: Adicionei as extensões de volta para o site funcionar!
            "/*.apk",
            "/*.png",
            "/*.jpg",
            "/*.jpeg",
            "/*.jfif",
            "/css/**",
            "/js/**"
    };

    // --- 2. Endpoints USER ---
    public static final String [] ENDPOINTS_USER = {
            "/users/atualizar/{id}",
            "/users/listarPorId/{id}",
            "/api/avatar/**",     // DICA: Use ** para facilitar, libera tudo de avatar
            "/api/dungeon/**",    // Libera tudo de dungeon
            "/api/desafio/**",
            "/api/recompensa/listar",
            "/api/recompensa/listarPorId/{id}"
    };

    // --- 3. Endpoints ADMIN ---
    public static final String [] ENDPOINTS_ADMIN = {
            "/users/listar",
            "/users/deletar/{id}",
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
                        // 1. FORÇA A LIBERAÇÃO DO LOGIN (Garante que é POST)
                        .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/criar").permitAll()

                        // 2. Libera o resto dos arquivos públicos (Site, Swagger)
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()

                        // Libera OPTIONS (necessário para o App não dar erro de CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 3. Rotas de ADMIN
                        .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")

                        // 4. Rotas de USER
                        .requestMatchers(ENDPOINTS_USER).authenticated()

                        // 5. O resto exige login
                        .anyRequest().authenticated()
                )
                // Adiciona o filtro, mas o Spring Security cuida para não rodar nas rotas permitAll
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Configuração permissiva para evitar dor de cabeça com React Native
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*")); // Libera todos os headers

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