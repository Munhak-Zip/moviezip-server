package com.example.moviezip.domain;

import com.example.moviezip.domain.chat.ChatRoom;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    private String nickname;
    private String hint;
    private Role role; // 역할을 추가

    public User(Long id) {
        this.id = id;
    }
    public User( String userId, String password) {
        this.userId = userId;
        this.password = password;
    }


}
