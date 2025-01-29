package com.example.moviezip.controller.chat;

import com.example.moviezip.domain.CustomUserDetails;
import com.example.moviezip.domain.User;
import com.example.moviezip.domain.chat.ChatMessage;
import com.example.moviezip.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    // 특정 채팅방의 메시지 목록 조회
    @GetMapping("chat/room/{roomId}/messages")
    public ResponseEntity<List<ChatMessage>> getMessagesByRoomId(@PathVariable String roomId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByChatRoomId(roomId);
        log.debug("Messages fetched for roomId {}: {}", roomId, messages);
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/send/message")
    @SendTo("/topic/chat")
    public ChatMessage sendMessage(ChatMessage message, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            log.info("customUserDetails.. = " + customUserDetails.getAuthorities());
            message.setSender(message.getSender());
            message.setChatRoomId(message.getChatRoomId());
            chatMessageService.saveMessage(message);
        } else {
            // 인증되지 않은 경우 처리 (예: 메시지 전송 불가)
            message.setSender("이용자"); // 예시로 "anonymous" 설정
        }
        return message;
    }
}
