package com.example.moviezip.util;

import com.example.moviezip.domain.CustomUserDetails;
import com.example.moviezip.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class jwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${token.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRATION;
    @Value("${token.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRATION;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //클레임 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //JWT에서 userId 추출
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token); // JWT에서 모든 claims 가져오기
        return claims.get("userId", Long.class); // userId 추출 (Long 타입)
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);  // JWT에서 모든 claims 가져오기
        return (List<String>) claims.get("roles");  // roles는 이제 List<String> 형태로 저장됨
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        CustomUserDetails customUser = (CustomUserDetails) userDetails;
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = customUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // "ROLE_XXX" 형태의 문자열을 추출
                .collect(Collectors.toList());

        claims.put("userId", customUser.getUser());
        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> additionalClaims, String subject) {
        return Jwts.builder()
                .setClaims(additionalClaims) // 추가 클레임 설정
                .setSubject(subject) // 사용자 이름 (subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 서명 알고리즘과 비밀 키 설정
                .compact(); // JWT 토큰 생성
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    //accessToken 재발급
    public String refreshAccessToken(String refreshToken) {

        validateRefreshToken(refreshToken);

        String username = extractUsername(refreshToken);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return generateToken(userDetails);

    }

    //refreshToken 유효성 검증
    public void validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(refreshToken);
            Claims claims = extractAllClaims(refreshToken);

            if (claims.getIssuedAt().after(new Date())) {
                throw new CustomException(ExceptionStatus.PREMATURE_TOKEN);
            }

            Date expireAt = claims.getExpiration();

            if (expireAt.before(new Date())) {
                throw new CustomException(ExceptionStatus.EXPIRED_TOKEN);
            }
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ExceptionStatus.INVALID_TOKEN);
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}