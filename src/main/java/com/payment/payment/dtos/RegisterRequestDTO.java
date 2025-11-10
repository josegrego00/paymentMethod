package com.payment.payment.dtos;

/**
 * PLANTILLA REUTILIZABLE - Register Request DTO
 */
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;

    // Constructores
    public RegisterRequestDTO() {}
    
    public RegisterRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}