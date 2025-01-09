package com.example.moviezip.controller;

import com.example.moviezip.domain.RefreshToken;
import com.example.moviezip.domain.User;
import com.example.moviezip.domain.jwt.AuthenticationRequest;
import com.example.moviezip.domain.jwt.AuthenticationResponse;
import com.example.moviezip.service.CustomUserDetailsService;
import com.example.moviezip.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.moviezip.util.jwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${token.refresh-token-expire-time}")
    private long refreshTokenExpirationTime;

    private final RefreshTokenService refreshTokenService;


    //사용자의 id,pw를 검증
    //jwtUtil을 호출해 Token을 생성하고 JwtResponse에 Token을 담아 return ResponseEntity
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String accessToken = jwtTokenUtil.generateToken(userDetails);


        RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(userDetails, refreshTokenExpirationTime);


        return ResponseEntity.ok(new AuthenticationResponse(accessToken,refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) throws Exception {

        String refresh = refreshToken.trim();

        // Check if the token starts with "Bearer " and remove it
        if (refresh.startsWith("Bearer ")) {
            refresh = refreshToken.substring(7); // Remove the "Bearer " prefix
        }
        // refresh token 유효성 검사
        if (!refreshTokenService.validateRefreshToken(refresh)) {
            throw new Exception("유효하지 않은 refresh token입니다.");
        }

        // 데이터베이스에서 해당 refresh token을 가져옴
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.getRefreshToken(refresh);
        if (refreshTokenOptional.isEmpty()) {
            throw new Exception("Refresh token not found");
        }


        String newAccessToken = jwtTokenUtil.refreshAccessToken(refresh);
//        // refresh token과 연관된 사용자 정보 가져오기
//        User user = refreshTokenOptional.get().getUser();
//
//        // 새로운 JWT (Access token) 생성
//        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserId());
//        String newAccessToken = jwtTokenUtil.generateToken(userDetails);

        // 새로 생성된 access token 반환
        return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, refresh));
    }
}
