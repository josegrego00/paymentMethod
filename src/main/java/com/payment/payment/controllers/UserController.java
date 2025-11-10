package com.payment.payment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.payment.payment.dtos.UserDTO;
import com.payment.payment.mapper.UserMapper;
import com.payment.payment.models.User;
import com.payment.payment.services.UserServices;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserMapper mapper;

    @PostMapping("user")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = mapper.toEntity(userDTO);
        User userSave = userServices.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

}
