package com.senac.rpgsaude.config;

import com.senac.rpgsaude.security.SecurityFilter; // Importa o filtro que criamos na outra pasta
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter; // ✅ Usamos o filtro de Token (igual ao do seu amigo)

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // 🔓 LIBERA O DOWNLOAD E A PÁGINA INICIAL
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/index.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/rpgsaude/**").permitAll() // Para o proxy
                        .requestMatchers(HttpMethod.GET, "/static/**").permitAll()

                        // 🔓 LIBERA LOGIN E REGISTRO
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        // 🔒 BLOQUEIA O RESTO
                        .anyRequest().authenticated()
                )
                // Adiciona o nosso filtro de token antes do filtro padrão
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ✅ Se as senhas no banco forem "123456" (texto puro), use o NoOp (comentado abaixo).
        // ✅ Se forem criptografadas (hashes longos), use o BCrypt (padrão).
        return new BCryptPasswordEncoder();
        // return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }
}