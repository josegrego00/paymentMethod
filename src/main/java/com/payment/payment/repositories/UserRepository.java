package com.payment.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.payment.payment.models.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    // ✅ AGREGAR ESTOS MÉTODOS NUEVOS
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
