package com.payment.payment.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionDTO {

    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    private UserResponseDTO user;

    @NotNull(message = "El ID del método de pago es obligatorio")
    private PaymentMethodDTO paymentMethod;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser un valor positivo")
    private Double amount;

    @NotBlank(message = "El estado de la transacción es obligatorio")
    private String status;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime timestamp;

    public TransactionDTO() {}

    public TransactionDTO(Long id, UserResponseDTO user, PaymentMethodDTO paymentMethod, Double amount, String status, LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public PaymentMethodDTO getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
