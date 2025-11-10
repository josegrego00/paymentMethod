package com.payment.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.payment.payment.dtos.PaymentMethodDTO;
import com.payment.payment.dtos.TransactionDTO;
import com.payment.payment.dtos.UserDTO;
import com.payment.payment.models.PaymentMethod;
import com.payment.payment.models.Transaction;
import com.payment.payment.models.User;

@Mapper(componentModel = "spring", uses = { UserMapper.class, PaymentMethodMapper.class })
public interface TransactionMapper {

   // Para CONSULTAR
    @Mapping(target = "user", source = "user")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    TransactionDTO toDTO(Transaction transaction);

    // Para CREAR - MapStruct maneja la conversión automáticamente
    @Mapping(target = "user", source = "user")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    Transaction toEntity(TransactionDTO transactionDTO);

     default Transaction toEntityWithReferences(Transaction transaction, User user, PaymentMethod paymentMethod){
        transaction.setUser(user);
        transaction.setPaymentMethod(paymentMethod);
        return transaction;
    }

}