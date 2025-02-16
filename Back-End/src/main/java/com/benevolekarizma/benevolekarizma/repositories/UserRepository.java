package com.benevolekarizma.benevolekarizma.repositories;

import com.benevolekarizma.benevolekarizma.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<Object> findByEmailAndPassword(String email, String password);

    Optional<User> findByUsername(String userName);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
}
