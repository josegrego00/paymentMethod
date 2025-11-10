package com.payment.payment.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payment.dtos.TransactionDTO;
import com.payment.payment.mapper.TransactionMapper;
import com.payment.payment.models.Transaction;
import com.payment.payment.services.TransactionServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/payment")
@Tag(name = "Payment Transactions", description = "API para gestión de transacciones de pago")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionMapper transactionMapper;

    @Operation(summary = "Listar transacciones", description = "Obtiene todas las transacciones existentes")
    @GetMapping("/")
    public ResponseEntity<List<TransactionDTO>> listTransaction() {
        try {
            List<Transaction> listTransactions = transactionServices.listTransactions();
            if (!listTransactions.isEmpty()) {
                logger.info("Retornando lista de transacciones - Total: {}", listTransactions.size());
                List<TransactionDTO> listDTO = new ArrayList<>();
                for (Transaction transaction : listTransactions) {
                    listDTO.add(transactionMapper.toDTO(transaction));
                }
                return ResponseEntity.status(HttpStatus.OK).body(listDTO);
            }
            logger.warn("No se encontraron transacciones - Retornando 204");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            logger.error("Error inesperado al listar transacciones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener transacción por ID", description = "Busca una transacción específica por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        Optional<Transaction> transactionOptional = transactionServices.getTransaction(id);
        if (transactionOptional.isPresent()) {
            logger.info("Transacción encontrada - ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.toDTO(transactionOptional.get()));
        } else {
            logger.warn("Transacción no encontrada - ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Crear transacción", description = "Registra una nueva transacción de pago")
    @PostMapping("/")
    public ResponseEntity<TransactionDTO> addTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        Transaction transactionSave = transactionServices.saveTransaction(transaction);
        logger.info("Transacción creada exitosamente - ID: {}", transactionSave.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionMapper.toDTO(transactionSave));
    }

    @Operation(summary = "Actualizar transacción", description = "Actualiza los datos de una transacción existente")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id,
            @Valid @RequestBody TransactionDTO transactionDTO) {
        Optional<Transaction> transactionOptional = transactionServices.getTransaction(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionServices.updateTransaction(id,
                    transactionMapper.toEntity(transactionDTO));
            logger.info("Transacción actualizada - ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.toDTO(transaction));
        } else {
            logger.warn("Transacción no encontrada para actualizar - ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Eliminar transacción", description = "Elimina una transacción por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        Optional<Transaction> transactionOptional = transactionServices.getTransaction(id);
        if (transactionOptional.isPresent()) {
            transactionServices.deleteTransaction(id);
            logger.info("Transacción eliminada - ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.warn("Transacción no encontrada para eliminar - ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}