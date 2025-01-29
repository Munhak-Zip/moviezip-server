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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    //사용자 정보를 바탕으로 토큰을 생성하기 위해 createToken을 호출하는 상위 메서드
    //userId와 roles도 추출
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

    //실제 JWT 토큰을 생성하는 역할을 하는 하위 메서드
    // 이 메서드는 claims와 subject를 받아서 JWT 토큰을 빌드하고 서명하여 반환 함.
    private String createToken(Map<String, Object> additionalClaims, String subject) {
        return Jwts.builder()
                .setClaims(additionalClaims) // 추가 클레임 설정
                .setSubject(subject) // 사용자 이름 (subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 서명 알고리즘과 비밀 키 설정
                .compact(); // JWT 토큰 생성
    }

    //refresh토큰 생성
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // refreshToken을 발급받은 후, 쿠키에 저장하는 코드
    public void setRefreshTokenInCookie(String refreshToken, HttpServletResponse response) {

        // Refresh Token을 HttpOnly 쿠키에 저장
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);    // JavaScript에서 접근 불가
        cookie.setSecure(false);      // HTTPS 환경에서만 전송되도록 설정 (필요한 경우)
        cookie.setPath("/");         // 모든 경로에서 사용 가능하도록 설정
        cookie.setMaxAge((int) REFRESH_TOKEN_EXPIRATION);  // 유효 기간 설정

        response.addCookie(cookie);     // 응답에 쿠키 추가
        System.out.println("쿠키에 refreshToken 설정: " + refreshToken);
    }

    //refreshToken 검증 및 AccessToken발급
    public String refreshAccessToken(String refreshToken) {
        log.info("refreshToken:" + refreshToken);
        // refreshToken 유효성 검증
        if (!validateRefreshToken(refreshToken)) {
            throw new CustomException(ExceptionStatus.INVALID_TOKEN);  // 유효하지 않은 토큰인 경우 예외 던짐
        }
        //클레임 추출
        Claims claims = extractAllClaims(refreshToken);

        //refreshToken의 발행 일자가 현재보다 이후라면?
        if (claims.getIssuedAt().after(new Date())) {
            throw new CustomException(ExceptionStatus.PREMATURE_TOKEN);
        }

        Date expireAt = claims.getExpiration();

        if (expireAt.before(new Date())) {
            throw new CustomException(ExceptionStatus.EXPIRED_TOKEN);
        }

        String username = extractUsername(refreshToken);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return generateToken(userDetails); // 새로운 AccessToken 발급

    }

    public boolean validateRefreshToken(String refreshToken) {
        // 유효성 검사: JWT가 유효한지, 만료되지 않았는지 등
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)  // JWT 서명 키
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}