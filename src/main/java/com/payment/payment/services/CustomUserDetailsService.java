package com.payment.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.payment.payment.models.User;
import com.payment.payment.repositories.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
        User user=userRepository.findByUsernameOrEmail(username).orElseThrow(() -> new RuntimeException("User no encontrado"));
        
        return user;

    }

}
