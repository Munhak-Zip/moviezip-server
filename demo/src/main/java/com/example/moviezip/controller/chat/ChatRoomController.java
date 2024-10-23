package com.example.moviezip.controller.chat;

import com.example.moviezip.dao.mongo.chat.ChatMessageRepository;
import com.example.moviezip.domain.chat.ChatRoom;
import com.example.moviezip.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;

    //모든 채팅방 리스트 조회
    /*사용 예시
    Admin이 채팅방 리스트를 조회할 때:
    GET /chat?userId=1&isAdmin=true
    User가 자신의 채팅방 리스트를 조회할 때:
    GET /chat?userId=2&isAdmin=false
    */

    @GetMapping
    public ResponseEntity<List<ChatRoom>> getChatRooms(@RequestParam Long userId, @RequestParam boolean isAdmin) {
        List<ChatRoom> chatRooms;
        if (isAdmin) {
            chatRooms = chatRoomService.getAllChatRooms();
        } else {
            chatRooms = chatRoomService.getUserChatRooms(userId);
        }
        return ResponseEntity.ok(chatRooms);
    }
    
    //채팅방 생성
    @PostMapping("/createRoom")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestParam Long userId, @RequestParam Long adminId){
        ChatRoom ChatRoom = chatRoomService.createRoom(adminId, userId);
        return ResponseEntity.ok(ChatRoom);
    }


    // 특정 채팅방 조회
    @GetMapping("/room/{RoomId}")
    @ResponseBody
    public Optional<ChatRoom> getRoom(@PathVariable String RoomId) {
        return chatRoomService.findByRoomId(RoomId);
    }

}
