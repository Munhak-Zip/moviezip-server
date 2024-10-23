package com.example.moviezip.service.chat;

import com.example.moviezip.dao.mongo.chat.ChatRoomRepository;
import com.example.moviezip.dao.mybatis.MybatisUserDao;
import com.example.moviezip.domain.User;
import com.example.moviezip.domain.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MybatisUserDao mybatisUserDao;
    //채팅방 생성
    public ChatRoom createRoom(Long adminId, Long userId) {

        User admin = mybatisUserDao.getUserById2(adminId); // admin 정보를 MybatisUserDao로 가져옴
        User user = mybatisUserDao.getUserById2(userId); // user 정보를 MybatisUserDao로 가져옴
        log.info("admin: "+ admin);
        log.info("user: "+ user);
        ChatRoom chatRoom = new ChatRoom(admin, user);
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    //모든 채팅방 조회(admin용)
    public List<ChatRoom> getAllChatRooms(){
        return chatRoomRepository.findAll();
    }
    //사용자가 참여한 채팅방만 조회
    public List<ChatRoom> getUserChatRooms(Long userId) {
        System.out.println("유저아이디:"+ userId);
        return chatRoomRepository.findByUserId(userId);
    }
    public Optional<ChatRoom> findByRoomId(String roomid){
        return chatRoomRepository.findById(roomid);
    }
}
