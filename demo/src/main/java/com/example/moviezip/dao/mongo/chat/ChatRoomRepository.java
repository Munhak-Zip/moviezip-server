package com.example.moviezip.dao.mongo.chat;

import com.example.moviezip.domain.chat.ChatRoom;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableMongoRepositories
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    // 추가적인 쿼리 메소드를 정의할 수 있습니다.

    // 사용자의 ID로 채팅방을 찾는 메소드
    List<ChatRoom> findByUserId(Long userId); // 쿼리 메소드 사용

    @NotNull
    Optional<ChatRoom> findById(@NotNull String roomId);

}