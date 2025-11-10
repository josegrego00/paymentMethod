package com.payment.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.payment.payment.dtos.PaymentMethodDTO;
import com.payment.payment.models.PaymentMethod;



@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE= Mappers.getMapper(PaymentMethodMapper.class);
    PaymentMethodDTO toDTO (PaymentMethod transaction);
    PaymentMethod toEntity(PaymentMethodDTO paymentMethodDTO);
}


