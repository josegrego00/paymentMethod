package com.payment.payment.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.payment.models.User;
import com.payment.payment.repositories.UserRepository;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> listUser() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userNew) {

        return userRepository.findById(id).map(user -> {
            user.setPassword(userNew.getPassword());
            user.setRole(userNew.getRole());
            user.setUsername(userNew.getUsername());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    public void deleteUser(Long id) {
        
        userRepository.deleteById(id);
    }


}
