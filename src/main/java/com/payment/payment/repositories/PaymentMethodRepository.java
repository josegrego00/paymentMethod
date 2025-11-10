package com.payment.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.payment.models.PaymentMethod;



public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>{

}
