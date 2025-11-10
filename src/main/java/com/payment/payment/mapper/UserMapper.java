package com.payment.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.payment.payment.dtos.UserDTO;
import com.payment.payment.dtos.UserResponseDTO;
import com.payment.payment.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    UserResponseDTO toResponseDTO(User user);

    User toEntity(UserDTO userDTO);

}
