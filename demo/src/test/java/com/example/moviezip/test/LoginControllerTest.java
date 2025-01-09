package com.example.moviezip.test;

import com.example.moviezip.controller.LoginController;
import com.example.moviezip.domain.RefreshToken;
import com.example.moviezip.domain.User;
import com.example.moviezip.domain.jwt.AuthenticationRequest;
import com.example.moviezip.domain.jwt.AuthenticationResponse;
import com.example.moviezip.service.CustomUserDetailsService;
import com.example.moviezip.service.RefreshTokenService;
import com.example.moviezip.util.jwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private jwtUtil jwtTokenUtil;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private RefreshTokenService refreshTokenService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("로그인 인증 성공 테스트")
    void 로그인_성공() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("testUser", "password");
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        RefreshToken refreshToken = RefreshToken.builder()
                .token("refresh-token-value")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("access-token-value");
        when(refreshTokenService.createOrUpdateRefreshToken(eq(userDetails), any(Long.class)))
                .thenReturn(refreshToken);

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token-value"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token-value"));
    }

    @Test
    @DisplayName("로그인 인증 실패 테스트")
    void 로그인_실패() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("testUser", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Incorrect username or password"));

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void refreshTokenSuccess() throws Exception {
        // Mock a valid refresh token
        String validRefreshToken = "valid_refresh_token";
        String newAccessToken = "new_access_token";

        // 리프레시 토큰 서비스에서 유효한 리프레시 토큰을 반환하도록 설정
        when(refreshTokenService.validateRefreshToken(validRefreshToken)).thenReturn(true);
        when(refreshTokenService.getRefreshToken(validRefreshToken)).thenReturn(Optional.of(new RefreshToken())); // 이 부분은 mock data로 설정
        when(jwtTokenUtil.generateToken(any())).thenReturn(newAccessToken); // 새로 생성된 액세스 토큰 반환

        // MockMvc를 사용하여 API 호출 테스트
        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\": \"" + validRefreshToken + "\"}"))
                .andExpect(status().isOk()) // 200 OK 응답
                .andExpect(jsonPath("$.accessToken").value(newAccessToken)) // 새로운 액세스 토큰이 반환되었는지 확인
                .andExpect(jsonPath("$.refreshToken").value(validRefreshToken)); // 기존 리프레시 토큰이 반환되는지 확인

        // verify that the service method was called
        verify(refreshTokenService).validateRefreshToken(validRefreshToken);
        verify(refreshTokenService).getRefreshToken(validRefreshToken);
        verify(jwtTokenUtil).generateToken(any());
    }

    @Test
    @DisplayName("리프레시 토큰 유효하지 않을 때 실패 테스트")
    void 리프레시_토큰_실패() throws Exception {
        String invalidRefreshToken = "invalid-refresh-token";

        when(refreshTokenService.validateRefreshToken(invalidRefreshToken)).thenReturn(false);

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRefreshToken)))
                .andExpect(status().is4xxClientError());
    }
}
