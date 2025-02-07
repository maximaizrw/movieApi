package com.maxi.movieapi.security.repository;

import com.maxi.movieapi.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail(String username);
}
