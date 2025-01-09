package com.example.moviezip.service;

import com.example.moviezip.domain.CustomUserDetails;
import com.example.moviezip.domain.RefreshToken;
import com.example.moviezip.domain.User;
import com.example.moviezip.repository.RefreshTokenRepository;
import com.example.moviezip.util.jwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final jwtUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    //RefreshToken 저장(이미 있으면 갱신)
    @Transactional
    public RefreshToken createOrUpdateRefreshToken(UserDetails userDetails, long expirationTimeInSeconds) {

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        Long userId = customUserDetails.getUser();

        // UserDetails에서 username을 받아서 refresh 토큰을 생성
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(expirationTimeInSeconds);
        System.out.println("만료기간:"+ expiryDate);
        // 기존 refreshToken이 있는지 확인
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(userId);

        if (existingToken.isPresent()) {
            // 기존 토큰이 있으면 갱신
            RefreshToken token = existingToken.get();
            token.updateToken(refreshToken, expiryDate); // 기존 토큰 갱신
            return refreshTokenRepository.save(token);
        } else {
            // 기존 토큰이 없으면 새로 생성
            RefreshToken newRefreshToken = RefreshToken.builder()
                    .userId(userId) // UserDetails로부터 User 객체 생성
                    .token(refreshToken)
                    .expiryDate(expiryDate)
                    .build();
            return refreshTokenRepository.save(newRefreshToken);
        }
    }

    // Refresh Token 검증
    public boolean validateRefreshToken(String token) {
        // Retrieve token from the database
        System.out.println("Incoming token: " + token);
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token.trim());

        if (refreshTokenOpt.isEmpty()) {
            // Log the incoming token and the one from the database for debugging
            System.out.println("Token not found in DB. Incoming token: " + token);
            return false;
        }

        RefreshToken refreshToken = refreshTokenOpt.get();

        // Log both tokens for comparison
        System.out.println("Token from DB: " + refreshToken.getToken());

        // Check if the tokens match exactly
        return !refreshToken.isExpired();
    }


    //Refresh Token 조회
    public Optional<RefreshToken> getRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    //특정 사용자의 Refresh Token 삭제
    @Transactional
    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}

