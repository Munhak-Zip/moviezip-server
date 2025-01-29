package com.example.moviezip.domain.chat;

import com.example.moviezip.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "ChatRooms") // MongoDB Document
public class ChatRoom {

    @Id
    private String id;

    private String roomName;

    private Long adminId; // 관리자 ID (User 객체 대신)
    private Long userId;  // 일반 사용자 ID (User 객체 대신)

    private List<String> messageIds; // 메시지 ID 리스트 (OneToMany 대신 ID 참조)

    // 생성자
    public ChatRoom(Long adminId, Long userId) {
        this.adminId = adminId;
        this.userId = userId;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        this.roomName = "ChatRoom between " + adminId + " and " + userId + " on " + formattedDate;
    }
}
