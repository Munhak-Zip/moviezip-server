package com.example.moviezip.test;

import com.example.moviezip.controller.LoginController;
import com.example.moviezip.domain.jwt.AuthenticationRequest;
import com.example.moviezip.domain.jwt.AuthenticationResponse;
import com.example.moviezip.service.CustomUserDetailsService;
import com.example.moviezip.util.jwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private jwtUtil jwtTokenUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        // MockMvc를 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void testCreateAuthenticationToken_Success() throws Exception {
        // Arrange: AuthenticationRequest와 Mock 서비스 응답 준비
        AuthenticationRequest request = new AuthenticationRequest("testUser", "password");
        AuthenticationResponse response = new AuthenticationResponse("valid_jwt_token");

        // Mocking
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(mock(UserDetails.class));
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("valid_jwt_token");

        // Act and Assert: POST 요청으로 로그인 시도
        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testUser\", \"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("valid_jwt_token"));

        // Verify interactions
        verify(userDetailsService).loadUserByUsername("testUser");
        verify(jwtTokenUtil).generateToken(any(UserDetails.class));
    }

    @Test
    void testCreateAuthenticationToken_Fail_BadCredentials() throws Exception {
        // Arrange: 잘못된 사용자 입력
        AuthenticationRequest request = new AuthenticationRequest("wrongUser", "wrongPassword");

        // Mocking
        when(userDetailsService.loadUserByUsername("wrongUser")).thenThrow(new BadCredentialsException("Bad credentials"));

        // Act and Assert: 로그인 실패 시나리오
        mockMvc.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"wrongUser\", \"password\": \"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Incorrect username or password"));

        // Verify interactions
        verify(userDetailsService).loadUserByUsername("wrongUser");
    }

    @Test
    void testMain() throws Exception {
        // Act and Assert: main 페이지 정상 접근
        mockMvc.perform(get("/main"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome!! This is main page"));
    }

    @Test
    void testHello() throws Exception {
        // Act and Assert: hello 페이지 정상 접근
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }
}
