package com.example.moviezip.controller;

import com.example.moviezip.service.RefreshTokenService;
import com.example.moviezip.util.jwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LogoutController {

    @Autowired
    private jwtUtil jwtTokenUtil;

    private final RefreshTokenService refreshTokenService;

    /**
     * 로그아웃 API
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // 요청에서 Authorization 헤더 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Access Token이 필요합니다.");
        }

        // Access Token 추출
        String accessToken = authorizationHeader.substring(7);

        // Access Token에서 username 추출
        Long userId = jwtTokenUtil.extractUserId(accessToken);

        // Refresh Token 삭제 (DB)
        refreshTokenService.deleteRefreshToken(userId);

        return ResponseEntity.ok("로그아웃 성공");
    }
}
