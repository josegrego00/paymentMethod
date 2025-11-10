package com.payment.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.payment.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    
}
