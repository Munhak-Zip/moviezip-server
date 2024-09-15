package com.example.moviezip.controller;


import com.example.moviezip.domain.*;
import com.example.moviezip.service.CustomUserDetailsService;
import com.example.moviezip.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private HttpSession httpSession; // HttpSession 주입

    //회원가입
    @PostMapping("/joinProc")
    public ResponseEntity<String> joinProcess(@RequestBody User joinDTO) {
        System.out.println(joinDTO.getUserId());
        return userService.joinProcess(joinDTO);
    }
    //로그인한 사용자의 아이디 반환  -> 사실상 필요 없음
    @GetMapping("/user-id")
    public ResponseEntity<String> getUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired");
        }
        System.out.println("ddd"+userDetails.getUsername());
        return ResponseEntity.ok(userDetails.getUsername());
    }

    // 사용자 고유 아이디 받아오는 컨트롤러
    @GetMapping("/getId")
    public ResponseEntity<Long> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Return 401 if user is not authenticated
        }
        Long userId  = userDetails.getUser(); // CustomUserDetails에서 User 객체 추출
        System.out.println("sss"+userDetails.getUser());
        return ResponseEntity.ok().body(userId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addInterest")
    public ResponseEntity<Void> addInterest(@RequestBody InterestDTO interestDTO) {
        if (interestDTO.getUserId() == null || interestDTO.getGenre() == null) {
            return ResponseEntity.badRequest().build();
        }

        userService.addInterest(interestDTO.getUserId(), interestDTO.getGenre());
        return ResponseEntity.ok().build();
    }

    // 사용자 아이디 찾기
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/findUserId")
    public ResponseEntity<String> findUserIdByInfo(@RequestBody UserDto userDto) {
        // 디버깅을 위해 입력값을 출력합니다.

        String nickname = userDto.getNickname();
        String hint = userDto.getHint();
        System.out.println("Received nickname: " + nickname);
        System.out.println("Received hint: " + hint);

        String userId = userService.findUserIdByInfo(nickname, hint);

        // Debugging: 조회된 userId 확인
        System.out.println("Found userId: " + userId);

        if (userId != null) {
            return ResponseEntity.ok(userId); // userId를 문자열로 반환
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //입력한 id가 db에 있는지? true or false 넘겨주기
    @PostMapping("/checkExistsId")
    public ResponseEntity<Boolean> findPwStepOne(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        System.out.println("사용자 아이디: " + userId);
        boolean userExists = userService.checkUserExistsById(userId);
        return ResponseEntity.ok(userExists);
    }

    //비번 수정
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/changePassword")
    public void changePassword(@RequestBody Map<String, String> payload) {
        Long id = Long.valueOf(payload.get("userId"));
        System.out.println("사용자 아이디:!!!! " + id);
        String newPw = payload.get("newPassword");
        String newPassword = passwordEncoder.encode(newPw);
        System.out.println("사용자 아이디: " + id);
        System.out.println("새 비밀번호: " + newPassword);
        userService.updateUserPassword(id,newPassword);
    }

    @PostMapping("/checkExistInterestById")
    public Boolean checkExistInterestById(@RequestBody Map<String, Long> payload) {
        Long id = payload.get("id");
        System.out.println("사용자 아이디: " + id);
        return userService.findInterest(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/mypage/user")
    public User findMypageUser(@RequestParam long userId) {
        System.out.println("마이페이지 사용자"+userId);
        User u = userService.getUserById2(userId);
        System.out.println("사용자"+ u.getUserId());
        return u;
    }

    //나의 관심사 체크
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/mypage/user/interest")
    public Interest findMypageInterest(@RequestParam long userId) {
        System.out.println("마이페이지 사용자"+userId);
        Interest i = userService.findInterest2(userId);
        System.out.println("사용자"+ i.getGenre());
        return i;
    }

    //비번 확인
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/mypage/checkUserPass")
    public Boolean checkUserPassword(@RequestParam long userId, @RequestParam String password) {
        User u = userService.getUserById2(userId);
        System.out.println(userId);
        System.out.println(u.getNickname());

        // DB에 저장된 암호화된 비밀번호 가져오기
        String encryptedPassword = u.getPassword();

        // BCryptPasswordEncoder를 이용하여 비밀번호를 비교
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 입력된 비밀번호를 암호화된 비밀번호와 비교
        if (passwordEncoder.matches(password, encryptedPassword)) {
            System.out.println("비밀번호가 일치합니다.");
            return true;
        } else {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return false;
        }
    }
}