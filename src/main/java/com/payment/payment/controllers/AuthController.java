package com.payment.payment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payment.dtos.AuthResponseDTO;
import com.payment.payment.dtos.LoginRequestDTO;
import com.payment.payment.dtos.RegisterRequestDTO;
import com.payment.payment.models.User;
import com.payment.payment.repositories.UserRepository;
import com.payment.payment.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * PLANTILLA REUTILIZABLE - Auth Controller
 * 
 * 📋 ENDPOINTS:
 * ✅ POST /auth/login → Iniciar sesión
 * ✅ POST /auth/register → Registrar nuevo usuario
 * 
 * 🚀 USO EN OTROS PROYECTOS:
 * 1. Copiar esta clase
 * 2. Cambiar package y imports
 * 3. Ajustar DTOs según tu entidad User
 * 4. Listo!
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autenticación y registro")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 🔐 LOGIN DE USUARIO
     */
    @Operation(summary = "Iniciar sesión", description = "Autentica usuario y devuelve JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // 1. AUTENTICAR con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. OBTENER usuario autenticado
            User user = (User) authentication.getPrincipal();

            // 3. GENERAR token JWT
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            // 4. RETORNAR respuesta
            AuthResponseDTO response = new AuthResponseDTO(token, "Login exitoso");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            AuthResponseDTO response = new AuthResponseDTO(null, "Credenciales inválidas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * 📝 REGISTRO DE NUEVO USUARIO
     */
    @Operation(summary = "Registrar usuario", description = "Crea una nueva cuenta de usuario")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            // 1. VERIFICAR si usuario ya existe
            if (userRepository.findByUsername(registerRequest.getUsername()).isPresent() ||
                userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                AuthResponseDTO response = new AuthResponseDTO(null, "Usuario o email ya existen");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            // 2. CREAR nuevo usuario
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // 🔐 Encriptar
            user.setRole("USER"); // Rol por defecto

            // 3. GUARDAR usuario
            userRepository.save(user);

            // 4. GENERAR token automáticamente
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            AuthResponseDTO response = new AuthResponseDTO(token, "Usuario registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            AuthResponseDTO response = new AuthResponseDTO(null, "Error en el registro");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}