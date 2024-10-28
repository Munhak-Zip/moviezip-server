package com.example.moviezip.domain.chat;

import com.esotericsoftware.kryo.NotNull;
import com.example.moviezip.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "ChatRooms") // MongoDB Document
public class ChatRoom {

    @Id
    private String id;

    private String roomName;

    @NotNull
    @ManyToOne
    private User admin; // 관리자로 설정된 사용자

    @NotNull
    @ManyToOne
    private User user; // 일반 사용자들

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>(); // 채팅 메시지들

    // userId를 받는 생성자 추가
    public ChatRoom(User admin, User user) {
        this.admin = admin;
        this.user = user;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        this.roomName = "ChatRoom between " + admin.getNickname() + " and " + user.getNickname() + " on " + formattedDate;
    }
}
