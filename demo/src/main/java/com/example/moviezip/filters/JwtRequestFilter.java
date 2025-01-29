package com.example.moviezip.filters;

import com.example.moviezip.service.CustomUserDetailsService;
import com.example.moviezip.service.RefreshTokenService;
import com.example.moviezip.util.jwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.moviezip.domain.RefreshToken;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

// 클라이언트의 요청을 인터셉트해서 헤더에 포함된 토큰이 유효한지 검증하고,
// 유효한 토큰일 경우 Spring Security의 Authentication 객체를 설정하여 현재 사용자가 인증된 상태임을 지정하는 방법
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private jwtUtil jwtutil;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

//        // /refresh 엔드포인트는 필터를 거치지 않도록 처리
        if (request.getRequestURI().equals("api/auth/refresh")) {
            chain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwt = null;
        final String authorizationHeader = request.getHeader("Authorization");


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtutil.extractUsername(jwt);
        }

        // 쿠키에서 refreshToken 추출
        if (jwt == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshToken".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        username = jwtutil.extractUsername(jwt);  // refreshToken의 사용자 이름도 추출
                        break;
                    }
                }
            }
        }

        // 토큰이 존재하고, Authentication 객체가 없다면 SecurityContext 설정
        UserDetails userDetails = null;
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userDetails = this.userDetailsService.loadUserByUsername(username);

            // 2. validateToken 으로 토큰 유효성 검사
            // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
            if (jwtutil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // 만약 accessToken이 만료되고 refreshToken이 유효하다면 /refresh 요청 처리 후 새로운 토큰 발급
        if (jwt == null || !jwtutil.validateToken(jwt, userDetails)) {
            String newAccessToken = refreshToken(request, response); // refreshToken 처리 후 새로운 AccessToken을 반환받음
            if (newAccessToken != null) {
                String newUsername = jwtutil.extractUsername(newAccessToken);
                UserDetails newUserDetails = userDetailsService.loadUserByUsername(newUsername);

                // 새로운 토큰 유효성 검사 후 인증 정보 설정
                if (jwtutil.validateToken(newAccessToken, newUserDetails)) {
                    UsernamePasswordAuthenticationToken newAuthToken =
                            new UsernamePasswordAuthenticationToken(newUserDetails, null, newUserDetails.getAuthorities());
                    newAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(newAuthToken);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private String refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = null;
        String username = null;

        // 1. 헤더에서 "Authorization" 추출 (Bearer <JWT>)
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            refreshToken = authorizationHeader.substring(7);
        }

        // 2. 헤더에 refreshToken이 없으면 쿠키에서 refreshToken 추출
        if (refreshToken == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshToken".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (refreshToken == null) {
            return null;  // refreshToken이 없으면 null 반환
        }

        // refresh token 유효성 검사
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            return null;  // 유효하지 않으면 null 반환
        }

        // 데이터베이스에서 해당 refresh token을 가져옴
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.getRefreshToken(refreshToken);
        if (refreshTokenOptional.isEmpty()) {
            return null;  // Refresh token을 찾을 수 없으면 null 반환
        }

        // 새로운 JWT (Access token) 생성
        return jwtutil.refreshAccessToken(refreshToken);
    }
}
