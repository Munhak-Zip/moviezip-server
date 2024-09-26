package com.example.moviezip.test;

import com.example.moviezip.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//로그인 흐름 테스트
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoginWithValidCredentials() throws Exception {
        String username = "mintss";
        String password = "mintss";
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        // Mocking UserDetails
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, encodedPassword, new ArrayList<>());

        // CustomUserDetailsService 동작 Mocking
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Mocking Authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, new ArrayList<>());

        // AuthenticationManager 동작 Mocking
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);

        // Perform the request
        mockMvc.perform(post("/loginProc")
                        .param("username", username)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        String username = "mint";
        String password = "mintss";
        String encodedPassword = bCryptPasswordEncoder.encode("mint"); // correct password

        // 올바른 암호화된 비밀번호를 가진 UserDetails Mocking
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, encodedPassword, new ArrayList<>());

        // CustomUserDetailsService 동작 Mocking
        when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // AuthenticationManager가 예외를 던지도록 Mocking
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // 요청 수행
        mockMvc.perform(post("/loginProc")
                        .param("username", username)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isUnauthorized());
    }

    //비밀번호 인코딩 및 검증 테스트
    @Test
    public void testPasswordEncryptionAndValidation() {
        String rawPassword = "mintss";
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

        // 암호화된 비밀번호를 데이터베이스에 저장한다고 가정
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("mint", encodedPassword, new ArrayList<>());

        // 사용자가 로그인 시 입력한 비밀번호
        String inputPassword = "mintss";

        // 입력한 비밀번호와 암호화된 비밀번호가 일치하는지 검증
        boolean matches = bCryptPasswordEncoder.matches(inputPassword, encodedPassword);

        // 비밀번호가 일치하는지 확인
        assertTrue(matches, "The encoded password should match the raw password.");
    }
}
