package com.example.moviezip.repository;

import com.example.moviezip.domain.RefreshToken;
import com.example.moviezip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 특정 사용자(User) 기반으로 RefreshToken 조회
    Optional<RefreshToken> findByUserId(Long userId);

    // Refresh Token 값으로 조회
    Optional<RefreshToken> findByToken(String token);

    // 특정 사용자의 Refresh Token 삭제
    void deleteByUserId(Long userId);
}
