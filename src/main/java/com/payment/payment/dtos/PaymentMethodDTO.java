package com.payment.payment.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class PaymentMethodDTO {

    private Long id;

    @NotBlank(message = "El tipo de método de pago es obligatorio")
    private String type;

    @NotBlank(message = "Los detalles del método de pago son obligatorios")
    private String details;


    public PaymentMethodDTO() {}

    public PaymentMethodDTO(Long id, String type, String details) {
        this.id = id;
        this.type = type;
        this.details = details;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
