package com.maxi.movieapi.security.repository;

import com.maxi.movieapi.security.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshTokenValue(String refreshTokenValue);
}
