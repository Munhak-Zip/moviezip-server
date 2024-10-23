package com.example.moviezip.dao.mongo.chat;

import com.example.moviezip.domain.chat.ChatMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableMongoRepositories
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    // 특정 채팅방의 메시지를 조회하는 메소드
    List<ChatMessage> findByChatRoomId(String chatRoomId);
}