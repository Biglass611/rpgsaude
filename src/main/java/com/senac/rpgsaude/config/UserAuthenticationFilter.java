package com.senac.rpgsaude.config;

import com.senac.rpgsaude.entity.Usuario;
import com.senac.rpgsaude.repository.UsuarioRepository;
import com.senac.rpgsaude.service.UserService.JwtTokenService; // Import correto do seu projeto
import com.senac.rpgsaude.service.UserService.UserDetailsImpl; // Import correto do seu projeto

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Verifica se o endpoint requer autenticação antes de processar
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);

            if (token != null) {
                // Valida o Token e pega o email (Subject)
                String subject = jwtTokenService.getSubjectFromToken(token);

                // Busca o usuário no banco (Get() pode lançar exceção se não achar, ideal tratar ou usar orElseThrow)
                Usuario usuario = usuarioRepository.findByEmail(subject).orElseThrow(() -> new RuntimeException("Usuário não encontrado no token."));

                // Cria o UserDetailsImpl (Wrapper)
                UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

                // Cria a autenticação do Spring
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                // Salva no contexto (Logado!)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Se o endpoint é privado e não tem token, lança erro (ou deixa o Spring barrar depois)
                // Geralmente deixamos passar null para o Spring Security decidir negar o acesso,
                // mas seguindo a lógica da sua amiga:
                throw new RuntimeException("O token está ausente.");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // CORREÇÃO AQUI: Mudamos de SecurityConfiguration para SecurityConfig
        return Arrays.stream(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).noneMatch(publicEndpoint ->
                requestURI.startsWith(publicEndpoint.replace("/**", ""))
        );
    }
}