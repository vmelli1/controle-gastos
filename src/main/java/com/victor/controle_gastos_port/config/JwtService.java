package com.victor.controle_gastos_port.config;

import com.victor.controle_gastos_port.usuario.model.Role;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Usuario usuario){
         return Jwts.builder()
                .subject(usuario.getEmail()) // sujeito
                .claim("role", usuario.getRole().name()) // reivindicaç~~ao
                .issuedAt(new Date()) // emitdo em
                .expiration(new Date(System.currentTimeMillis() + expiration)) // expiração
                .signWith(getSignKey()) //  assinar com
                .compact(); // compactar // current  time
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();      // ← faltou essa chamada final
    }




    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}


