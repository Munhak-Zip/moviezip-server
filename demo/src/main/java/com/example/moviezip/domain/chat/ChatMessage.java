package com.example.moviezip.domain.chat;

import com.example.moviezip.domain.Interest;
import com.example.moviezip.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "ChatMessages")
public class ChatMessage {

    public enum MessageType {
        GREETING, TALK
    }

    @Id
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageType type;
    private Long userId;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    private ChatRoom chatRoom; // 채팅방 정보

}
