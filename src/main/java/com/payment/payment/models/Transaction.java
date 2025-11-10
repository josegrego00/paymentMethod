package com.payment.payment.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    private Double amount;

    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private LocalDateTime timestamp;
   
   
    public Transaction() {
    }


    public Transaction(User user, PaymentMethod paymentMethod, Double amount, String status, LocalDateTime timestamp) {
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


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }


    public void setPaymentMethod(PaymentMethod paymentMethod) {
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
