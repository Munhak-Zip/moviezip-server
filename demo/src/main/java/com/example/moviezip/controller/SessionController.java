package com.example.moviezip.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    // Controller에서 세션 만료 시 401 상태 코드를 반환하는 엔드포인트 추가

        @GetMapping("/sessionId")
        public ResponseEntity<Map<String, String>> getSessionId(HttpServletRequest request) {
            HttpSession session = request.getSession(false); // 세션이 없다면 null 반환
            Map<String, String> response = new HashMap<>();
            if (session != null) {
                response.put("sessionId", session.getId()); // 세션 ID 반환
            } else {
                response.put("sessionId", "No active session");
            }
            return ResponseEntity.ok(response);
        }
        @GetMapping("/session-expired")
        public void sessionExpired(HttpServletResponse response) throws IOException {
            System.out.println("세션만료");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired");
        }
    }
