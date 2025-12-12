package com.senac.rpgsaude.config;

import com.senac.rpgsaude.entity.Usuario;
import com.senac.rpgsaude.repository.UsuarioRepository;
import com.senac.rpgsaude.service.UserService.JwtTokenService;
import com.senac.rpgsaude.service.UserService.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher; // IMPORTANTE: Importação necessária
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
        // Se entrou aqui, é porque deve validar (não é rota pública)
        String token = recoveryToken(request);

        if (token != null) {
            try {
                String subject = jwtTokenService.getSubjectFromToken(token);
                // Busca usuário. Se não achar, lança exceção (catch abaixo pega)
                Usuario usuario = usuarioRepository.findByEmail(subject)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

                UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

                // Autentica no contexto
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Se o token for inválido, malformado ou usuário não existe:
                // Apenas ignoramos e deixamos a requisição seguir "sem autenticação".
                // O Spring Security bloqueará logo em seguida se a página exigir login.
                // Isso evita erros 500 desnecessários.
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Diz ao filtro para NÃO rodar (retorna true) se for endpoint público.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Remove o Context Path (ex: /rpgsaude) da URL para bater com a lista do SecurityConfig
        String path = request.getRequestURI();
        if (request.getContextPath() != null && path.startsWith(request.getContextPath())) {
            path = path.substring(request.getContextPath().length());
        }

        // Se o path ficou vazio (raiz do projeto), considera "/"
        if (path.isEmpty()) {
            path = "/";
        }

        AntPathMatcher matcher = new AntPathMatcher();
        String finalPath = path;

        // Verifica se bate com a lista de liberados usando Matcher correto (aceita *.apk, **, etc)
        // Retorna TRUE se for para PULAR o filtro (ou seja, se for público)
        return Arrays.stream(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .anyMatch(pattern -> matcher.match(pattern, finalPath));
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}