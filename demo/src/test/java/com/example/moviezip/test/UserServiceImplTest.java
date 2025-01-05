package com.example.moviezip.test;

import com.example.moviezip.dao.mybatis.MybatisUserDao;
import com.example.moviezip.domain.User;
import com.example.moviezip.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class) // Mockito 확장자를 사용하여 Mockito를 JUnit 5와 통합
    @Slf4j
class UserServiceImplTest {

    @Mock
    private MybatisUserDao mybatisUserDao;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }


    @Test
    public void 회원가입성공() {
        // 준비 Arrange
        User user = new User();
        user.setUserId("testUser");
        user.setPassword("plainPassword");
        user.setNickname("TestUser");
        user.setHint("Userhint");

        // BCryptPasswordEncoder의 동작을 모킹하여 비밀번호 암호화
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encryptedPassword");

        // MybatisUserDao의 addUser 메소드를 호출할 때 아무 동작도 하지 않도록 설정(void라서)
        doNothing().when(mybatisUserDao).addUser(any(User.class));

        // 실행(Act)
        ResponseEntity<String> response = userServiceImpl.joinProcess(user);

        // 검증(Assert)
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully.", response.getBody());

        log.info("회원가입성공"+ response.getBody());

        // MybatisUserDao와 BCryptPasswordEncoder의 메소드 호출 검증
        verify(bCryptPasswordEncoder).encode(user.getPassword()); // 비밀번호 암호화가 호출되었는지 확인
        verify(mybatisUserDao).addUser(any(User.class)); // addUser 메소드가 호출되었는지 확인
    }

    @Test
    public void 필드수부족_회원가입실패() {
        // Arrange
        User user = new User();
        user.setUserId("testUser");
        //비밀번호, 힌트 부재

        // 실행(Act)
        ResponseEntity<String> response = userServiceImpl.joinProcess(user);

        // 검증(Assert)
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("All fields are required.", response.getBody());

        // MybatisUserDao와 BCryptPasswordEncoder의 메소드 호출이 없음을 검증
        verifyNoInteractions(mybatisUserDao, bCryptPasswordEncoder); // 두 객체의 메소드가 호출되지 않았음을 검증
    }

    @Test
    public void 비밀번호부재_회원가입실패() {

        User user = new User();
        user.setUserId("testUser");
        user.setPassword(null);
        user.setNickname("Test User");
        user.setHint("User hint");


        ResponseEntity<String> response = userServiceImpl.joinProcess(user);


        assertEquals(400, response.getStatusCodeValue());
        assertEquals("All fields are required.", response.getBody());


        verifyNoInteractions(mybatisUserDao, bCryptPasswordEncoder);
    }
}

