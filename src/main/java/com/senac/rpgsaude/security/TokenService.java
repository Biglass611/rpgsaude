package com.senac.rpgsaude.security;

import com.senac.rpgsaude.entity.Usuario; // Use sua entidade Usuario
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método gera o token recebendo o Usuario
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setIssuer("rpgsaude-api")
                .setSubject(usuario.getEmail()) // Usa o email como subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if ("rpgsaude-api".equals(claims.getIssuer())) {
                return claims.getSubject();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}