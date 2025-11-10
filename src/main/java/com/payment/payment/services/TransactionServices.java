package com.payment.payment.services;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.payment.dtos.TransactionDTO;
import com.payment.payment.mapper.TransactionMapper;
import com.payment.payment.models.*;
import com.payment.payment.repositories.PaymentMethodRepository;
import com.payment.payment.repositories.TransactionRepository;
import com.payment.payment.repositories.UserRepository;

@Service
public class TransactionServices {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServices.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    public Transaction saveTransaction(Transaction transaction) {
        logger.info("Creando nueva transacción - Usuario ID: {}, Método Pago ID: {}",
                transaction.getUser().getId(), transaction.getPaymentMethod().getId());

        User user = userRepository.findById(transaction.getUser().getId())
                .orElseThrow(() -> new RuntimeException("El Usuario no existe"));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(transaction.getPaymentMethod().getId())
                .orElseThrow(() -> new RuntimeException("No existe este metodo de pago"));

        Transaction transactionSave = transactionMapper.toEntityWithReferences(transaction, user, paymentMethod);
        Transaction saved = transactionRepository.save(transactionSave);

        logger.info("Transacción creada exitosamente - ID: {}", saved.getId());
        return saved;
    }

    public List<Transaction> listTransactions() {
        logger.info("Solicitando listado completo de transacciones");
        List<Transaction> transactions = transactionRepository.findAll();
        logger.info("Transacciones encontradas: {}", transactions.size());
        return transactions;
    }

    public Optional<Transaction> getTransaction(Long id) {
        logger.info("Buscando transacción - ID: {}", id);
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            logger.info("Transacción encontrada - ID: {}", id);
        } else {
            logger.warn("Transacción no encontrada - ID: {}", id);
        }
        return transaction;
    }

    public Transaction updateTransaction(Long id, Transaction transactionUpdate) {
        logger.info("Actualizando transacción - ID: {}", id);

        Transaction transactionSave = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error no existe la transaccion..."));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(transactionUpdate.getPaymentMethod().getId())
                .orElseThrow(() -> new RuntimeException("No existe esta Forma de pago"));
        User user = userRepository.findById(transactionUpdate.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Ususario no existe"));

        transactionSave.setPaymentMethod(paymentMethod);
        transactionSave.setUser(user);
        transactionSave.setAmount(transactionUpdate.getAmount());
        transactionSave.setStatus(transactionUpdate.getStatus());
        transactionSave.setTimestamp(transactionUpdate.getTimestamp());

        Transaction updated = transactionRepository.save(transactionSave);
        logger.info("Transacción actualizada exitosamente - ID: {}", id);
        return updated;
    }

    public void deleteTransaction(Long id) {
        logger.info("Eliminando transacción - ID: {}", id);
        transactionRepository.deleteById(id);
        logger.info("Transacción eliminada - ID: {}", id);
    }
}