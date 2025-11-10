package com.payment.payment.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * PLANTILLA REUTILIZABLE - JWT Utility Class
 * 
 * 📋 FUNCIONES:
 * ✅ Generar tokens JWT
 * ✅ Validar tokens  
 * ✅ Extraer información de tokens
 * 
 * 🚀 USO EN OTROS PROYECTOS:
 * 1. Copiar esta clase
 * 2. Cambiar el package
 * 3. Listo!
 */
@Component
public class JwtUtil {
    
    // 🔐 CLAVE SECRETA - Cambiar en producción por variable de entorno
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
        "miClaveSecretaMuyLargaParaQueFuncioneBien123456".getBytes()
    );
    
    // ⏰ TIEMPO DE EXPIRACIÓN (24 horas)
    private final long expirationMs = 86400000; 

    /**
     * 📝 GENERAR TOKEN JWT
     * @param username - nombre de usuario
     * @param role - rol del usuario (USER, ADMIN, etc.)
     * @return token JWT
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)                    // 👤 Usuario
                .claim("role", role)                    // 🎭 Rol 
                .setIssuedAt(new Date())                // 🕐 Fecha emisión
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // ⏰ Expiración
                .signWith(secretKey, SignatureAlgorithm.HS256) // 🔐 Firma
                .compact();
    }

    /**
     * ✅ VALIDAR TOKEN
     * @param token - token a validar
     * @return true si es válido, false si es inválido
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 🔍 EXTRAER USERNAME DEL TOKEN
     * @param token - token JWT
     * @return username del usuario
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 🔍 EXTRAER ROL DEL TOKEN  
     * @param token - token JWT
     * @return rol del usuario
     */
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}