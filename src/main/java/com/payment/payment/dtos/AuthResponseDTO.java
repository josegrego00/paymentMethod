package com.payment.payment.dtos;

public class AuthResponseDTO {
    private String token;
    private String message;

    // Constructor vacío
    public AuthResponseDTO() {}

    // Constructor con token
    public AuthResponseDTO(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}