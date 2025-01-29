package com.example.moviezip.util;

import com.example.moviezip.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.shaded.org.apache.kerby.kerberos.provider.token.JwtUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99) //구성된 인터셉터들 간의 작업 우선순위를 지정
public class FilterChannelInterceptor implements ChannelInterceptor {

    private final jwtUtil jwtutil;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {

        //STOMP의 헤더에 직접 접근
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info(">>>>>> headerAccessor : {}", headerAccessor);
        assert headerAccessor != null;
        log.info(">>>>> headAccessorHeaders : {}", headerAccessor.getCommand());

        // CONNECT 또는 SEND 명령에 대해서만 처리
        if (Objects.equals(headerAccessor.getCommand(), StompCommand.CONNECT)
                || Objects.equals(headerAccessor.getCommand(), StompCommand.SEND)) {
            // Authorization 헤더가 있는지 확인
            String token = headerAccessor.getNativeHeader("Authorization") != null
                    ? removeBrackets(headerAccessor.getNativeHeader("Authorization").get(0))
                    : null;
            // Authorization 헤더에서 토큰 추출
            //String token = removeBrackets(String.valueOf(headerAccessor.getNativeHeader("Authorization")));
            log.info(">>>>>> Token received : {}", token);
            if (token != null) {
                try {
                    String userNameFromToken = jwtutil.extractUsername(token); // jwtUtil은 JWT 유틸리티 클래스
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userNameFromToken);
                    // 토큰 유효성 검사

                    // Authentication 객체 생성 및 SecurityContext에 설정
                    if(jwtutil.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }else{
                        throw new RuntimeException("Invalid or expired token");  // 적절한 예외 처리
                    }
                    // 토큰을 사용하여 사용자 ID 추출
                    Long userId = jwtutil.extractUserId(token);


                    // WebSocket 헤더에 AccountId를 추가하여, 후속 메시지에서 사용할 수 있도록 함
                    headerAccessor.addNativeHeader("UserId", String.valueOf(userId));
                    log.info(">>>>>> AccountId added to header : {}", userId);

                } catch (Exception e) {
                    log.warn(">>>>> Authentication Failed in FilterChannelInterceptor : ", e);
                    // 인증 실패 시 예외 처리 (예: 401 Unauthorized 응답 처리)
                    throw new CustomException(ExceptionStatus.INVALID_TOKEN);
                }
            } else {
                log.warn(">>>>>> Authorization header is missing");
            }
        }
        return message;

    }

    // Authorization 헤더에서 대괄호([])를 제거하는 메서드
    public String removeBrackets(String token) {
        // Trim and log the token before processing
        token = token.trim();
        log.info("Token before processing: {}", token);  // 디버깅 로그 추가


        if (token.startsWith("[") && token.endsWith("]")) {
            token = token.substring(1, token.length() - 1);
            log.info("Token after removing brackets: {}", token);  // 대괄호 제거 후 토큰 로그
        }

        // Remove "Bearer " prefix if it exists
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " (7 characters) 제거
            log.info("Token after removing Bearer: {}", token);  // Bearer 제거 후 토큰 로그
        }

        return token;
    }


}
