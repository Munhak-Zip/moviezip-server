package com.example.moviezip.service;

import com.example.moviezip.domain.CustomUserDetails;
import com.example.moviezip.domain.RefreshToken;
import com.example.moviezip.repository.RefreshTokenRepository;
import com.example.moviezip.util.jwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final jwtUtil jwtTokenUtil;
    @Value("${token.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRATION;

    //RefreshToken 저장(이미 있으면 갱신)
    @Transactional
    public RefreshToken createOrUpdateRefreshToken(UserDetails userDetails, long expirationTimeInSeconds, HttpServletResponse response) {
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        Long userId = customUserDetails.getUser();
        // UserDetails에서 username을 받아서 refresh 토큰을 생성
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        LocalDateTime expiryDate = LocalDateTime.now().plus(Duration.ofMillis(REFRESH_TOKEN_EXPIRATION));
        log.info("만료기간:" + expiryDate);

        // 기존 refreshToken이 있는지 확인
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(userId);
        RefreshToken tokenToReturn;

        if (existingToken.isPresent()) {         // 기존 토큰이 있으면 갱신
            RefreshToken token = existingToken.get();
            token.updateToken(refreshToken, expiryDate);
            tokenToReturn = refreshTokenRepository.save(token);
        } else {// 기존 토큰이 없으면 새로 생성
            RefreshToken newRefreshToken = RefreshToken.builder()
                    .userId(userId)
                    .token(refreshToken)
                    .expiryDate(expiryDate)
                    .build();
            tokenToReturn = refreshTokenRepository.save(newRefreshToken);
        }

        Cookie cookie = new Cookie("refreshToken", tokenToReturn.getToken());
        cookie.setHttpOnly(true);    // JavaScript에서 접근 불가
        cookie.setSecure(true);      // HTTPS 환경에서만 전송되도록 설정 (필요한 경우)
        cookie.setPath("/");         // 모든 경로에서 사용 가능하도록 설정
        cookie.setMaxAge((int) REFRESH_TOKEN_EXPIRATION);  // 유효 기간 설정
        response.addCookie(cookie);
        log.info("쿠키 설정: refreshToken={}, MaxAge={}", tokenToReturn, REFRESH_TOKEN_EXPIRATION);
        return tokenToReturn;
    }

    // Refresh Token 검증
    public boolean validateRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token.trim());
        if (refreshTokenOpt.isEmpty()) {
            log.info("Token not found in DB. Incoming token: " + token);
            return false;
        }
        RefreshToken refreshToken = refreshTokenOpt.get();
        log.info("Token from DB: " + refreshToken.getToken());
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

