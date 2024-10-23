package com.example.moviezip.service.chat;

import com.example.moviezip.dao.mongo.chat.ChatMessageRepository;
import com.example.moviezip.domain.chat.ChatMessage;
import com.example.moviezip.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
    }

    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    // 특정 채팅방의 메시지 목록 조회
    public List<ChatMessage> getMessagesByChatRoomId(String roomId) {
        return chatMessageRepository.findByChatRoomId(roomId);
    }

}
